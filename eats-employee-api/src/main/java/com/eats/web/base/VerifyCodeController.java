package com.eats.web.base;

import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.linlinjava.litemall.core.SMS.AliyunSMS;
import org.linlinjava.litemall.core.SMS.MobileCodeVerifyUtil;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.RegexUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by machao on 2018/8/16.
 */
@Api(description = "验证码相关API")
@RequestMapping("verifyCode")
@RestController
public class VerifyCodeController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MobileCodeVerifyUtil mobileCodeVerifyUtil;
    @Autowired
    private AliyunSMS aliyunSMS;


    @ApiOperation(value="获取校验码")
    @ApiImplicitParam(name = "body" ,value = "{\"mobile\":\"15101178304\"}",required = true)
    @RequestMapping(value = "getVerifyCode",method = RequestMethod.POST)
    public Object getVerifyCode(@RequestBody String body) {
        String mobile = JacksonUtil.parseString(body, "mobile");
        //String password = JacksonUtil.parseString(body, "password");

        if(!RegexUtil.isMobileExact(mobile)){
            return ResponseUtil.fail(403, "手机号格式不正确");
        }

        //1、获取redis中是否有该手机号对应的key，如果有说明获取过验证码，此时不在发送
        boolean hasKey = redisTemplate.hasKey(mobile);
        if (hasKey)
            return ResponseUtil.fail(403, "您已获取验证码,请稍后在获取...");

        //2、发送验证码，并将验证码保存至redis，设置过期时间
        String  code = mobileCodeVerifyUtil.saveMobileCodeToRedis(mobile);

        //3、发送校验码至手机
        try {
            aliyunSMS.sendSmsByAliyun(mobile,code);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("code", code);
        return ResponseUtil.ok(result);
    }

   /* @ApiOperation(value="获取校验码")
    @ApiImplicitParam(name = "body" ,value = "{\"mobile\":\"15101178304\",\"code\":\"15101178304\"}",required = true)
    @RequestMapping(value = "getVerifyCode",method = RequestMethod.POST)
    public Object getVerifyCode(@RequestBody String body) {
        String mobile = JacksonUtil.parseString(body, "mobile");

    }*/
}
