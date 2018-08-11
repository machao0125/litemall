package org.linlinjava.litemall.db.chao.service;

import org.linlinjava.litemall.db.chao.domain.Employee;

/**
 * Created by machao on 2018/8/10.
 */
public interface IEmployeeService {

    Employee login(String username,String password);

    boolean register(Employee record);

    int updatePassword(String mobile, String password);

    Employee getOne(Integer id);

    Employee selectByUsername(String username);

    Employee selectByMobile(String mobile);

    int updateEmployee(Employee employee);


}
