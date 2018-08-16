package com.eats.web.base;

import com.eats.domain.UserToken;
import com.eats.util.IpUtil;
import com.eats.util.UserTokenManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.SMS.MobileCodeVerifyUtil;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.RegexUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.chao.domain.Employee;
import org.linlinjava.litemall.db.chao.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(description = "员工登录相关API")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final Log logger = LogFactory.getLog(AuthController.class);

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private MobileCodeVerifyUtil mobileCodeVerifyUtil;


    @ApiOperation(value="员工登录")
    @ApiImplicitParam(name = "body" ,value = "{\"username\":\"machao\",\"password\":\"123456\"}",required = true)
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Object login(@RequestBody String body) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        if(username == null || password == null){
            return ResponseUtil.badArgument();
        }

        Employee login;

        login = employeeService.selectByUsername(username);
        if(null == login){
            return ResponseUtil.fail(403, "账号不存在");
        }
        login = employeeService.login(username, password);
        if(null == login){
            return ResponseUtil.fail(403, "账号密码不对");
        }

        // token
        UserToken userToken = UserTokenManager.generateToken(login.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", userToken.getToken());
        result.put("tokenExpire", userToken.getExpireTime());
        result.put("employee", login);
        return ResponseUtil.ok(result);
    }



    @ApiOperation(value="员工注册")
    @ApiImplicitParam(name = "body", value = "{\"username\":\"machao\",\"password\":\"123456\",\"mobile\":\"15101178304\",\"code\":\"111111\"}",paramType = "body",required = true)
    @RequestMapping(value = "register" , method = RequestMethod.POST)
    public Object register(@RequestBody String body,HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");

        if(username == null || password == null || mobile == null || code == null){
            return ResponseUtil.badArgument();
        }
        Employee temp;

        temp = employeeService.selectByUsername(username);
        if(null != temp){
            return ResponseUtil.fail(403, "用户名已注册");
        }

        temp = employeeService.selectByMobile(mobile);
        if(null != temp){
            return ResponseUtil.fail(403, "手机号已注册");
        }
        if(!RegexUtil.isMobileExact(mobile)){
            return ResponseUtil.fail(403, "手机号格式不正确");
        }


        Employee employee = new Employee();
        employee.setUsername(username);
        employee.setPassword(password);
        employee.setMobile(mobile);
        employee.setAddTime(new Date());
        employee.setWeixinOpenid("");
        employee.setNickname(username);
        employee.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");
        employee.setGender((byte)0);
        employee.setUserLevel((byte)0);
        employee.setStatus((byte)0);
        employee.setLastLoginTime(new Date());
        employee.setLastLoginIp(IpUtil.client(request));
        employee.setAddTime(new Date());

        employeeService.register(employee);

        // token
        UserToken userToken = UserTokenManager.generateToken(employee.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", userToken.getToken());
        result.put("tokenExpire", userToken.getExpireTime().toString());
        result.put("employee", employee);
        return ResponseUtil.ok(result);
    }

    @ApiOperation(value="员工重置密码")
    @ApiImplicitParam(name = "body", value = "{\"password\":\"123456\",\"mobile\":\"15101178304\",\"code\":\"111111\"}",paramType = "body",required = true)
    @PostMapping("resetPassword")
    public Object reset(@RequestBody String body,HttpServletRequest request) {
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");

        if(mobile == null || code == null || password == null){
            return ResponseUtil.badArgument();
        }
        if(!RegexUtil.isMobileExact(mobile)){
            return ResponseUtil.fail(403, "手机号格式不正确");
        }

        Employee temp = employeeService.selectByMobile(mobile);
        if(null == temp){
            return ResponseUtil.fail(403, "手机号未注册");
        }

        boolean verify = mobileCodeVerifyUtil.mobileCodeVerify(mobile, code);
        if (!verify){
            return ResponseUtil.fail(403, "验证码校验失败");
        }

        employeeService.updatePassword(mobile,password);

        return ResponseUtil.ok();
    }


}
