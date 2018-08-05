package com.chao.service.impl;

import com.chao.domain.Mall;
import com.chao.mapper.MallMapper;
import com.chao.service.IMallService;
import com.chao.utils.LongiAndLatiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.MalformedLinkException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by machao on 2018/8/5.
 */
@Service
public class MallServiceServiceImpl implements IMallService {

    @Autowired
    private MallMapper mallMapper;

    public List<Mall> mallListNearby(String longiandlatitude,int num){
        //1、获取所有商铺列表
        List<Mall> malls = mallMapper.selectAll();
        //2、计算所有商铺到用户的距离，并保存进店铺对象
        for (Mall mall : malls) {
            String mallPoint = mall.getLongiandlatitude();
            double distance = LongiAndLatiUtil.getDistance(longiandlatitude, mallPoint);
            mall.setDistance(distance);
        }
        //3、将店铺根据到用户的距离进行排序
        Collections.sort(malls, new Comparator<Mall>() {
            @Override
            public int compare(Mall o1, Mall o2) {
                double distance = o1.getDistance() - o2.getDistance();
                if (distance < 0){
                    return -1;
                }else if (distance == 0){
                    return 0;
                }else {
                    return 1;
                }
            }
        });
        //4、返回指定的店铺列表的数目
        return malls.subList(0,num);
    }
}
