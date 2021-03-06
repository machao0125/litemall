package com.eats.web.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.chao.domain.Mall;
import org.linlinjava.litemall.db.chao.service.IMallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by machao on 2018/8/10.
 */

@Api(description = "店铺相关API")
@RestController
@RequestMapping("/malls")
public class MallController {
    private final Log logger = LogFactory.getLog(MallController.class);

    @Autowired
    private IMallService mallService;

    @ApiOperation(value="获取员工店铺")
    @ApiImplicitParam(name = "body", value = "{\"empId\":1}",paramType = "body",required = true)
    @PostMapping("mallListByEmpId")
    public Object mallListByEmpId(@RequestBody String body) {
        Integer empId = JacksonUtil.parseInteger(body, "empId");
        //1、判断入参不为空
        if (empId == null) {
            return ResponseUtil.badArgument();
        }
        logger.info("empId: " + empId);

        //2、调用service方法获取店铺列表
        Mall mall = mallService.mallListByUserId(empId);

        return ResponseUtil.ok(mall);
    }
}
