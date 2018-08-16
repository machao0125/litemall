package org.linlinjava.litemall.core.SMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by machao on 2018/8/11.
 */
@Component
public class MobileCodeVerifyUtil {

    @Value("${sms.timeout.time}")
    private int time;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 保存校验码到redis，并设置过期时间
     *
     * @param mobile 手机号
     * @return
     */
    public String saveMobileCodeToRedis(String mobile){
        String mobileCode = getMobileCode();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(mobile,mobileCode , time, TimeUnit.SECONDS);
        return mobileCode;
    }

    /**
     * 验证码校验
     *
     * @param mobile 手机号
     * @param code 验证码
     * @return 校验是否成功
     */
    public boolean mobileCodeVerify(String mobile,String code) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        //1、判断redis中是否有这个key，没有直接失败
        boolean hasKey = redisTemplate.hasKey(mobile);
        if (!hasKey)
            return false;

        //2、判断这个code是否和redis中存储的一致，不一致失败
        String redisCode = operations.get(mobile);
        if (!redisCode.equals(code))
            return false;

        //3、删除当前的key，删除失败，检验失败
        /*Boolean delete = redisTemplate.delete(mobile);
        if (!delete)
            return false;*/
        return true;
    }

    /**
     * 生成校验码
     *
     * @return
     */
    public static String getMobileCode(){
        String sources = "0123456789";
        Random rand = new Random();
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < 6; j++) {
            flag.append(sources.charAt(rand.nextInt(sources.length())));
        }
        return flag.toString();
    }
}
