package org.linlinjava.litemall.wx.wxController;


import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.chao.domain.Mall;
import org.linlinjava.litemall.db.chao.service.IMallService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by machao on 2018/8/5.
 */
@RestController
@RequestMapping("/wx/malls")
public class MallContrloller {

    @Autowired
    private IMallService mallService;

    @PostMapping("mallListNearBy")
    public Object mallListNearBy(@LoginUser Integer userId, String longiandlatitude,@RequestParam(value = "num", defaultValue = "1") int num){
        //1、判断入参不为空
        if(longiandlatitude == null){
            return ResponseUtil.badArgument();
        }
        //2、调用service方法获取店铺列表
        List<Mall> malls = mallService.mallListNearby(longiandlatitude, num);

        return ResponseUtil.ok(malls);
    }

    @PostMapping("mallListByUserId")
    public Object mallListByUserId(@LoginUser Integer userId) {
        //1、判断入参不为空
        if (userId == null) {
            return ResponseUtil.badArgument();
        }
        //2、调用service方法获取店铺列表
        Mall mall = mallService.mallListByUserId(userId);

        return ResponseUtil.ok(mall);
    }
}
