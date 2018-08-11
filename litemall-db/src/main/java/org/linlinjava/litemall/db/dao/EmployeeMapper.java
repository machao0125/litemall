package org.linlinjava.litemall.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.chao.domain.Employee;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Integer id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    Employee selectByUsername(String username);

    Employee selectByMobile(String mobile);

    int updatePasswordByMobile(@Param("mobile") String mobile, @Param("password") String password);

}