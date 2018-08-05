package org.linlinjava.litemall.wx.wxController;

import com.chao.domain.Mall;
import com.chao.service.impl.MallServiceServiceImpl;
import javassist.bytecode.stackmap.BasicBlock;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by machao on 2018/8/5.
 */
@RestController
@RequestMapping("/wx/malls")
public class MallContrloller {

    @Autowired
    private MallServiceServiceImpl mallServiceService;

    @PostMapping("mallListNearBy")
    public Object mallListNearBy(@LoginUser Integer userId, String longiandlatitude, int num){
        //1、判断入参不为空
        if(longiandlatitude == null){
            return ResponseUtil.badArgument();
        }
        //2、设置默认返回店铺数量为1
        if (num == 0 )
            num = 1;
        //3、调用service方法获取店铺列表
        List<Mall> malls = mallServiceService.mallListNearby(longiandlatitude, num);

        return ResponseUtil.ok(malls);
    }
}
