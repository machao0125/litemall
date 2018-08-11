package com.eats.web.business;

import com.eats.annotation.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.chao.service.IMallService;
import org.linlinjava.litemall.db.chao.service.IOrderService;
import org.linlinjava.litemall.db.chao.utils.PageBean;
import org.linlinjava.litemall.db.chao.vo.QueryOrderVO;
import org.linlinjava.litemall.db.chao.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.LitemallOrderGoods;
import org.linlinjava.litemall.db.service.LitemallOrderGoodsService;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by machao on 2018/8/11.
 */
@Api(description = "订单相关API")
@RestController
@RequestMapping("/order")
public class OrderController {
    private final Log logger = LogFactory.getLog(OrderController.class);

    @Autowired
    private IOrderService orderService;
    @Autowired
    private LitemallOrderGoodsService orderGoodsService;
    @Autowired
    private IMallService mallService;

 /*    @ApiOperation(value="获取订单信息")
    @ApiImplicitParam(name = "orderId",value = "订单id" ,paramType = "query" , required = true, dataType = "int")
   @PostMapping("mallListByEmpId")
    public Object getOrder(String orderId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size){
        return ResponseUtil.ok();
    }*/
    /**
     * 订单列表
     *
     * @param vo     QueryOrderVO
     * @return 订单操作结果
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * data: xxx ,
     * count: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @ApiOperation(value="获取订单信息")
    @ApiImplicitParam(name = "vo",value = "查询订单vo" , required = true, dataType = "QueryOrderVO")
    @PostMapping("list")
    public Object list(@RequestBody QueryOrderVO vo) {
        /*if (userId == null) {
            return ResponseUtil.unlogin();
        }*/
        PageBean<LitemallOrder> bean = orderService.findItemByPageAndVO(vo);

        return ResponseUtil.ok(bean);
    }

    /**
     * 订单详情
     *
     * @param userId  用户ID
     * @param orderId 订单信息
     * @return 订单操作结果
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * orderInfo: xxx ,
     * orderGoods: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
   /* @GetMapping("detail")
    public Object detail(@LoginUser Integer userId, Integer orderId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        // 订单信息
        LitemallOrder order = orderService.findById(orderId);
        if (null == order) {
            return ResponseUtil.fail(403, "订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.fail(403, "不是当前用户的订单");
        }
        Map<String, Object> orderVo = new HashMap<String, Object>();
        orderVo.put("id", order.getId());
        orderVo.put("orderSn", order.getOrderSn());
        orderVo.put("addTime", LocalDate.now());
        orderVo.put("consignee", order.getConsignee());
        orderVo.put("mobile", order.getMobile());
        orderVo.put("address", order.getAddress());
        orderVo.put("goodsPrice", order.getGoodsPrice());
        orderVo.put("freightPrice", order.getFreightPrice());
        orderVo.put("actualPrice", order.getActualPrice());
        orderVo.put("orderStatusText", OrderUtil.orderStatusText(order));
        orderVo.put("handleOption", OrderUtil.build(order));

        List<LitemallOrderGoods> orderGoodsList = orderGoodsService.queryByOid(order.getId());
        List<Map<String, Object>> orderGoodsVoList = new ArrayList<>(orderGoodsList.size());
        for (LitemallOrderGoods orderGoods : orderGoodsList) {
            Map<String, Object> orderGoodsVo = new HashMap<>();
            orderGoodsVo.put("id", orderGoods.getId());
            orderGoodsVo.put("orderId", orderGoods.getOrderId());
            orderGoodsVo.put("goodsId", orderGoods.getGoodsId());
            orderGoodsVo.put("goodsName", orderGoods.getGoodsName());
            orderGoodsVo.put("number", orderGoods.getNumber());
            orderGoodsVo.put("retailPrice", orderGoods.getPrice());
            orderGoodsVo.put("picUrl", orderGoods.getPicUrl());
            orderGoodsVo.put("goodsSpecificationValues", orderGoods.getSpecifications());
            orderGoodsVoList.add(orderGoodsVo);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("orderInfo", orderVo);
        result.put("orderGoods", orderGoodsVoList);
        return ResponseUtil.ok(result);

    }
*/
}
