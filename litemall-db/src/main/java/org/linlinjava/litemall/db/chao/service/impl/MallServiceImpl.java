package org.linlinjava.litemall.db.chao.service.impl;



import org.linlinjava.litemall.db.chao.domain.Employee;
import org.linlinjava.litemall.db.chao.domain.Mall;
import org.linlinjava.litemall.db.chao.service.IEmployeeService;
import org.linlinjava.litemall.db.dao.MallMapper;
import org.linlinjava.litemall.db.chao.service.IMallService;
import org.linlinjava.litemall.db.chao.utils.LongiAndLatiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by machao on 2018/8/5.
 */
@Service
public class MallServiceImpl implements IMallService {

    @Autowired
    private MallMapper mallMapper;
    @Autowired
    private IEmployeeService employeeService;

    /**
     * 根据经纬度获取附近店铺信息
     *
     * @param longiandlatitude 经纬度
     * @param num 返回店铺数量
     * @return
     */
    public List<Mall> mallListNearby(String longiandlatitude, int num){
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

    /**
     * 根据userid获取店员对应店铺信息
     *
     * @param empId 店员id
     * @return
     */
    @Override
    public Mall mallListByUserId(int empId) {
        //1、根据empId查询对应店员信息
        Employee employee = employeeService.getOne(empId);
        //2、根据mall_id查询商铺信息
        return mallMapper.selectByPrimaryKey(employee.getMallId());
    }
}
