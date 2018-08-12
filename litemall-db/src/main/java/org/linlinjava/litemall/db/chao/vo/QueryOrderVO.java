package org.linlinjava.litemall.db.chao.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * Created by machao on 2018/8/11.
 */
@ApiModel(value = "QueryOrderVO", description = "查询订单vo对象")
@Data
public class QueryOrderVO {
    @ApiModelProperty(value = "当前页")
    private int currentPage = 1;

    @ApiModelProperty(value = "页面容量")
    private int pageSize = 10;

    @ApiModelProperty(value = "员工账号id")
    private int empId;

    @ApiModelProperty(value = "开始查询时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    @ApiModelProperty(value = "结束查询时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @ApiModelProperty(value = "店铺id")
    private int mallId;

    @ApiModelProperty(value = "订单状态")
    private int status = 0;

    @ApiModelProperty(value = "用户id")
    private int userId;
}
