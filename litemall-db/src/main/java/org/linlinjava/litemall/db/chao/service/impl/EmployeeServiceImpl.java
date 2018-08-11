package org.linlinjava.litemall.db.chao.service.impl;

import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
import org.linlinjava.litemall.db.chao.domain.Employee;
import org.linlinjava.litemall.db.chao.service.IEmployeeService;
import org.linlinjava.litemall.db.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by machao on 2018/8/10.
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 通过用户名和密码进行登录
     *
     * @param username  用户名
     * @param password  密码
     * @return employee
     */
    @Override
    public Employee login(String username, String password) {
        Employee temp = employeeMapper.selectByUsername(username);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(null == temp || !encoder.matches(password, temp.getPassword())){
            return null;
        }

        return temp;
    }

    /**
     * 保存员工信息
     *
     * @param record  员工实体对象
     * @return boolean
     */
    @Override
    public boolean register(Employee record) {
        //1、判断是否存在这个用户名
        Employee temp = employeeMapper.selectByUsername(record.getUsername());
        if (temp != null)
            return false;

        //2、对密码进行加密
        String password = record.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        record.setPassword(encodedPassword);
        record.setAddTime(new Date());

        employeeMapper.insert(record);

        return true;
    }

    /**
     * 更改密码
     *
     * @param mobile 员工电话号
     * @param password 员工登录密码
     * @return int
     */
    @Override
    public int updatePassword(String mobile,String password) {
        //加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        return employeeMapper.updatePasswordByMobile(mobile, encodedPassword);
    }

    /**
     * 根据id查询员工信息
     *
     * @param id 员工id
     * @return Employee
     */
    @Override
    public Employee getOne(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据账户查询员工信息
     *
     * @param username 员工账号
     * @return Employee
     */
    @Override
    public Employee selectByUsername(String username) {
        return employeeMapper.selectByUsername(username);
    }

    /**
     * 根据手机号查询员工信息
     *
     * @param mobile 员工手机号
     * @return Employee
     */
    @Override
    public Employee selectByMobile(String mobile) {
        return employeeMapper.selectByMobile(mobile);
    }

    /**
     * 更新员工信息
     *
     * @param employee 员工实体
     * @return int
     */
    @Override
    public int updateEmployee(Employee employee) {
        return employeeMapper.updateByPrimaryKey(employee);
    }
}
