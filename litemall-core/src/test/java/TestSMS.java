import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.Application;
import org.linlinjava.litemall.core.SMS.AliyunSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by machao on 2018/8/15.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class TestSMS {

    @Autowired
    private AliyunSMS aliyunSMS;

    @Test
    public void testGet(){

        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = aliyunSMS.sendSmsByAliyun("18842408542", "123456");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
//请求成功
        }
    }
}


