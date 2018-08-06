package org.linlinjava.litemall.db.dao;


import org.apache.ibatis.annotations.Mapper;
import org.linlinjava.litemall.db.chao.domain.Mall;

import java.util.List;


public interface MallMapper {
    int deleteByPrimaryKey(Byte id);

    int insert(Mall record);

    Mall selectByPrimaryKey(Byte id);

    List<Mall> selectAll();

    int updateByPrimaryKey(Mall record);
}