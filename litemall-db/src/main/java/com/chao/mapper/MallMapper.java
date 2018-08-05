package com.chao.mapper;

import com.chao.domain.Mall;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MallMapper {
    int deleteByPrimaryKey(Byte id);

    int insert(Mall record);

    Mall selectByPrimaryKey(Byte id);

    List<Mall> selectAll();

    int updateByPrimaryKey(Mall record);
}