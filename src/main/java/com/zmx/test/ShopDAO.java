package com.zmx.test;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author : 钟鸣星
 * @date : 2019年12月06日
 */
@Mapper
public interface ShopDAO extends ExportExcelBaseDAO {

    /**
     * 获取全量数据
     *
     * @param startIndex
     * @param offset
     * @return
     */
    @Select("select id, product_id as productId, name, params, image_url as imageUrl, is_upper as isUpper," +
            "is_active as isActive, is_deleted as isDeleted, create_time as createTime, update_time as updateTime  " +
            "from t_shop limit #{startIndex},#{offset}")
    List<ShopEntity> findAll(@Param("startIndex") int startIndex, @Param("offset") int offset);

    /**
     * 统计表数量
     *
     * @return
     */
    @Select("select count(*) from t_shop")
    int findCount();
}
