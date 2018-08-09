package org.linlinjava.litemall.db.dao;


import org.linlinjava.litemall.db.chao.domain.Mall;

import java.util.List;


public interface MallMapper {
    int deleteByPrimaryKey(int id);

    int insert(Mall record);

    Mall selectByPrimaryKey(int id);

    List<Mall> selectAll();

    int updateByPrimaryKey(Mall record);
}