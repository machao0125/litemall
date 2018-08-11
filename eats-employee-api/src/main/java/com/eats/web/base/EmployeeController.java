package com.eats.web.base;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.chao.domain.Employee;
import org.linlinjava.litemall.db.chao.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    @ApiImplicitParam(name = "empId", value = "员工id", required = true, dataType = "int")
    @PostMapping("getEmployee")
    public Object getEmployee(Integer empId) {
        //1、判断入参不为空
        if (empId == null) {
            return ResponseUtil.badArgument();
        }
        Employee employee = employeeService.getOne(empId);
        return ResponseUtil.ok(employee);
    }

    @ApiOperation(value = "更新员工信息")
    @ApiImplicitParam(name = "employee", value = "员工",paramType = "query", required = true, dataType = "Employee")
    @PostMapping("updateEmployee")
    public Object updateEmployee(@RequestBody Employee employee) {
        //1、判断入参不为空
        if (employee == null) {
            return ResponseUtil.badArgument();
        }
        employeeService.updateEmployee(employee);
        return ResponseUtil.ok();
    }


}