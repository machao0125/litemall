package com.eats.web.base;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.chao.domain.Employee;
import org.linlinjava.litemall.db.chao.service.IEmployeeService;
import org.linlinjava.litemall.db.chao.service.IOrderService;
import org.linlinjava.litemall.db.chao.vo.QueryOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * Created by machao on 2018/8/11.
 */
@Api(description = "员工相关API")
@RestController
@RequestMapping("employee")
public class EmployeeController {
    private final Log logger = LogFactory.getLog(AuthController.class);

    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取员工信息")
    @ApiImplicitParam(name = "body", value = "{\"empId\":1}",paramType = "body",required = true)
    @PostMapping("getEmployee")
    public Object getEmployee(@RequestBody String body) {
        Integer empId = JacksonUtil.parseInteger(body, "empId");
        //1、判断入参不为空
        if (empId == null) {
            return ResponseUtil.badArgument();
        }
        Employee employee = employeeService.getOne(empId);
        return ResponseUtil.ok(employee);
    }

    @ApiOperation(value = "更新员工信息")
    @ApiImplicitParam(name = "employee", value = "员工", required = true, dataType = "Employee")
    @PostMapping("updateEmployee")
    public Object updateEmployee(@RequestBody Employee employee) {
        //1、判断入参不为空
        if (employee == null) {
            return ResponseUtil.badArgument();
        }
        employeeService.updateEmployee(employee);
        return ResponseUtil.ok();
    }


    @ApiOperation(value = "查询员工收入")
    @ApiImplicitParam(name = "vo", value = "{\"empId\":1,\"beginDate\":\"2018-08-10 09:41:37\",\"endDate\":\"2018-08-14 09:41:37\"}", required = true, dataType = "QueryOrderVO")
    @PostMapping("queryIncome")
    public Object queryIncome(@RequestBody QueryOrderVO vo) {
        //1、判断入参不为空
        if (vo == null) {
            return ResponseUtil.badArgument();
        }

        Map<String, Object> map = employeeService.queryIncomeByEmpIdAndDate(vo.getEmpId(), vo.getBeginDate(),vo.getEndDate());
        return ResponseUtil.ok(map);
    }
}