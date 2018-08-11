package org.linlinjava.litemall.db.chao.service.impl;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.chao.domain.LitemallOrder;
import org.linlinjava.litemall.db.chao.service.IOrderService;
import org.linlinjava.litemall.db.chao.utils.PageBean;
import org.linlinjava.litemall.db.chao.vo.QueryOrderVO;
import org.linlinjava.litemall.db.dao.LitemallOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by machao on 2018/8/11.
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private LitemallOrderMapper OrderMapper;

    @Override
    public LitemallOrder getOne(int orderId) {
        return OrderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public PageBean<LitemallOrder> findItemByPageAndVO(QueryOrderVO vo){
        //设置分页信息，必须在mapper接口中的方法执行之前设置该分页信息
        PageHelper.startPage(vo.getPage(), vo.getSize());

        List<LitemallOrder> allItems = OrderMapper.listByVO(vo); //全部商品
        int countNums = OrderMapper.countByVO(vo);                    //总记录数
        PageBean<LitemallOrder> pageData = new PageBean<>(vo.getPage(), vo.getSize(), countNums);
        pageData.setItems(allItems);
        return pageData;
    }
}
