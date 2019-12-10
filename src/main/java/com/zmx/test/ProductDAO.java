package com.zmx.test;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author : 钟鸣星
 * @date : 2019年12月06日
 */
@Mapper
public interface ProductDAO extends ExportExcelBaseDAO {

    /**
     * 获取全量数据
     *
     * @param startIndex
     * @param offset
     * @return
     */
    @Select("select id, type_id as typeId, brand_id as brandId, name, price, is_upper as isUpper" +
            ", is_active as isActive, is_deleted as isDeleted, create_time as createTime, update_time as updateTime " +
            "from t_product limit #{startIndex},#{offset}")
    List<ProductEntity> findAll(@Param("startIndex") int startIndex, @Param("offset") int offset);

    /**
     * 统计数量
     *
     * @return
     */
    @Select("select count(*) from t_product")
    int findCount();
}
