package org.linlinjava.litemall.db.chao.service;

import org.linlinjava.litemall.db.chao.domain.LitemallOrder;
import org.linlinjava.litemall.db.chao.utils.PageBean;
import org.linlinjava.litemall.db.chao.vo.QueryOrderVO;

/**
 * Created by machao on 2018/8/11.
 */
public interface IOrderService {

    LitemallOrder getOne(int orderId);

    PageBean<LitemallOrder> findItemByPageAndVO(QueryOrderVO vo);

}
