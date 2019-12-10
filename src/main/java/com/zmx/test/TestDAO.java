package com.zmx.test;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author : 钟鸣星
 * @date : 2019年12月05日
 */
@Mapper
public interface TestDAO {

    @Select("select count(*) from 3000wsql")
    int fincCount();

    @Select("select * from 3000wsql limit #{startIndex}, #{endIndex}")
    List<Map> findAll(int startIndex, int endIndex);

    @Select("select * from 3000wsql limit #{startIndex}, #{endIndex}")
    List<TestVO> testEasyExcel(int startIndex, int endIndex);
}
