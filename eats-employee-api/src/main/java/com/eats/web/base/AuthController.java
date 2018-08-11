package com.eats.web.base;

import com.eats.domain.UserToken;
import com.eats.util.IpUtil;
import com.eats.util.UserTokenManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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


    /**
     * 员工账号登录
     *
     * @param body 请求内容，{ username: xxx, password: xxx }
     * @param request 请求对象
     * @return 登录结果
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              token: xxx,
     *              tokenExpire: xxx,
     *              employee: xxx
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @ApiOperation(value="员工登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", paramType = "query" ,required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", paramType = "query" ,required = true, dataType = "String")
    })
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Object login(@RequestBody String body, HttpServletRequest request) {
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
        result.put("tokenExpire", userToken.getExpireTime().toString());
        result.put("employee", login);
        return ResponseUtil.ok(result);
    }


    /**
     * 账号注册
     *
     * @param body 请求内容
     *  {
     *      username: xxx,
     *      password: xxx,
     *      mobile: xxx
     *      code: xxx
     *  }
     *  其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              token: xxx,
     *              tokenExpire: xxx,
     *              userInfo: xxx
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @ApiOperation(value="员工注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", paramType = "query" , required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query" ,required = true, dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机号", paramType = "query" , required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "query" , required = true, dataType = "String")
    })
    @RequestMapping(value = "register" , method = RequestMethod.POST)
    public Object register(@RequestBody String body, HttpServletRequest request) {
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

    /**
     * 账号密码重置
     *
     * @param body 请求内容
     *  {
     *      password: xxx,
     *      mobile: xxx
     *      code: xxx
     *  }
     *  其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     *   成功则 { errno: 0, errmsg: '成功' }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @ApiOperation(value="员工重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password",  paramType = "query" ,required = true, dataType = "String"),
            @ApiImplicitParam(name = "mobile", paramType = "query" , required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", paramType = "query" , required = true, dataType = "String")
    })
    @PostMapping("resetPassword")
    public Object reset(@RequestBody String body, HttpServletRequest request) {
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

        employeeService.updatePassword(mobile,password);

        return ResponseUtil.ok();
    }


}
