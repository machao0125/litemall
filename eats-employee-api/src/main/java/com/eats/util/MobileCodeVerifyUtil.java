package com.eats.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by machao on 2018/8/11.
 */
public class MobileCodeVerifyUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public String saveMobileCodeToRedis(String mobile){
        String mobileCode = getMobileCode();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(mobile,mobileCode , 90, TimeUnit.SECONDS);
        return mobileCode;
    }

    public boolean mobileCodeVerify(String mobile,String code) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        boolean hasKey = redisTemplate.hasKey(mobile);
        if (!hasKey)
            return false;

        String redisCode = operations.get(mobile);
        if (!redisCode.equals(code))
            return false;

        return true;
    }

    public static String getMobileCode(){
        String sources = "0123456789";
        Random rand = new Random();
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < 6; j++)
        {
            flag.append(sources.charAt(rand.nextInt(sources.length())));
        }
        return flag.toString();
    }
}
