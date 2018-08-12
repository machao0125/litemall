package org.linlinjava.litemall.db.chao.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class LitemallOrder {
    private Integer id;

    private Integer userId;

    private String orderSn;

    private Short orderStatus;

    private String consignee;

    private String mobile;

    private String address;

    private BigDecimal goodsPrice;

    private BigDecimal freightPrice;

    private BigDecimal couponPrice;

    private BigDecimal integralPrice;

    private BigDecimal orderPrice;

    private BigDecimal actualPrice;

    private int actualNum;

    private String payId;

    private Date payTime;

    private String shipSn;

    private String shipChannel;

    private Date shipTime;

    private Date confirmTime;

    private Date endTime;

    private Date addTime;

    private Integer empId;

    private Boolean deleted;

    private Integer mallId;

    private BigDecimal returnMoney;

}