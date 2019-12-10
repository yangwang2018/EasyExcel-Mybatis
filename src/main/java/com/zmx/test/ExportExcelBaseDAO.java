package com.zmx.test;

import java.util.List;

/**
 * @author : 钟鸣星
 * @date : 2019年12月06日
 */
public interface ExportExcelBaseDAO {

    /**
     * 获取全量数据
     *
     * @param startIndex
     * @param offset
     * @return
     */
    List findAll(int startIndex, int offset);

    /**
     * 统计数量
     *
     * @return
     */
    int findCount();
}
