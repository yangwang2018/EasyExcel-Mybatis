package com.zmx.test;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author : 钟鸣星
 * @date : 2019年12月06日
 */
public class TestVO {

    @ExcelProperty("序号")
    private Integer id;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("地址")
    private String addr;
    @ExcelProperty("头衔")
    private String title;
    @ExcelProperty("备注")
    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
