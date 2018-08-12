package com.eats.web.business;

import com.eats.annotation.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.chao.service.IMallService;
import org.linlinjava.litemall.db.chao.service.IOrderService;
import org.linlinjava.litemall.db.chao.utils.DateUtil;
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


    @ApiOperation(value="获取订单列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "empId",value = "员工id" , paramType = "query",required = true, dataType = "String"),
        @ApiImplicitParam(name = "status",value = "订单状态" ,  paramType = "query",required = true, dataType = "String"),
        @ApiImplicitParam(name = "orderDate",value = "订单创建时间" , paramType = "query", required = true, dataType = "String"),
        @ApiImplicitParam(name = "currentPage",value = "当前页" , paramType = "query",required = true, dataType = "String"),
        @ApiImplicitParam(name = "pageSize",value = "页面容量" , paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("list")
    public Object list( int empId,int status,String orderDate,
                        @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        if (empId == 0) {
            return ResponseUtil.unlogin();
        }

        QueryOrderVO vo = new QueryOrderVO();
        vo.setEmpId(empId);
        vo.setCurrentPage(currentPage);
        vo.setPageSize(pageSize);
        vo.setStatus(status);
        vo.setBeginDate(DateUtil.getBeginDayTime(DateUtil.StringToDate(orderDate)));
        vo.setEndDate(DateUtil.getEndDayTime(DateUtil.StringToDate(orderDate)));
        PageBean<LitemallOrder> bean = orderService.findItemByPageAndVO(vo);

        return ResponseUtil.ok(bean);
    }


    @Autowired
    private IMallService mallService;

    /**
     * 订单详情
     *
     * @param empId  用户ID
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
    @ApiOperation(value="获取订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "empId", paramType = "query", value = "员工id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "orderId", paramType = "query", value = "订单id", required = true, dataType = "int")
    })
    @PostMapping("detail")
    public Object detail( Integer empId, Integer orderId) {
        if (empId == null) {
            return ResponseUtil.unlogin();
        }
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        // 订单信息
        LitemallOrder order = orderService.getOne(orderId);
        if (null == order) {
            return ResponseUtil.fail(403, "订单不存在");
        }
        if (!order.getEmpId().equals(empId)) {
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

}
