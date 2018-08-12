package org.linlinjava.litemall.db.chao.service.impl;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.chao.domain.Employee;
import org.linlinjava.litemall.db.chao.domain.LitemallOrder;
import org.linlinjava.litemall.db.chao.service.IEmployeeService;
import org.linlinjava.litemall.db.chao.service.IOrderService;
import org.linlinjava.litemall.db.chao.utils.PageBean;
import org.linlinjava.litemall.db.chao.vo.QueryOrderVO;
import org.linlinjava.litemall.db.dao.LitemallOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by machao on 2018/8/11.
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private LitemallOrderMapper OrderMapper;
    @Autowired
    private IEmployeeService employeeService;


    @Override
    public LitemallOrder getOne(int orderId) {
        return OrderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public PageBean<LitemallOrder> findItemByPageAndVO(QueryOrderVO vo){
        //设置分页信息，必须在mapper接口中的方法执行之前设置该分页信息
        PageHelper.startPage(vo.getCurrentPage(), vo.getPageSize());

        List<LitemallOrder> allItems = OrderMapper.listByVO(vo); //全部商品
        int countNums = OrderMapper.countByVO(vo);                    //总记录数
        PageBean<LitemallOrder> pageData = new PageBean<>(vo.getCurrentPage(), vo.getPageSize(), countNums);
        pageData.setItems(allItems);
        return pageData;
    }

    @Override
    public Map<String, Object> queryIncomeByEmpIdAndDate(int empId, Date beginDate, Date endDate) {
        HashMap<String, Object> map = new HashMap<>();
        QueryOrderVO vo = new QueryOrderVO();
        vo.setEmpId(empId);
        vo.setBeginDate(beginDate);
        vo.setEndDate(endDate);
        List<LitemallOrder> orders = OrderMapper.listByVO(vo);

        BigDecimal actualPrice=BigDecimal.ZERO;
        int orderCount = 0;
        int goodsCount = 0;
        for (LitemallOrder order : orders) {
            actualPrice =actualPrice.add(order.getActualPrice());
            goodsCount = goodsCount + order.getActualNum();
            orderCount ++;
        }

        Employee employee = employeeService.getOne(empId);
        map.put("userName",employee.getUsername());
        map.put("amountDelivery",actualPrice);//提货金额
        map.put("orderQuantityDelivery",orderCount);//提货订单数
        map.put("goodsQuantityDelivery",goodsCount);//提货数

        return map;
    }
}
