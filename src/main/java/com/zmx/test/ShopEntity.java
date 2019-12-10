package com.zmx.test;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

/**
 * @author : 钟鸣星
 * @date : 2019年12月06日
 */
public class ShopEntity {
    @ExcelProperty("序列号")
    private Integer id;
    @ExcelProperty("产品ID")
    private Integer productId;
    @ExcelProperty("名称")
    private String name;
    @ExcelProperty("参数")
    private String params;
    @ExcelProperty("图片路径")
    private String imageUrl;
    @ExcelProperty("是否上架")
    private Byte isUpper;
    @ExcelProperty("是否有效")
    private Byte isActive;
    @ExcelProperty("是否删除")
    private Byte isDeleted;
    @ExcelProperty("创建时间")
    private Date createTime;
    @ExcelProperty("修改时间")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Byte getIsUpper() {
        return isUpper;
    }

    public void setIsUpper(Byte isUpper) {
        this.isUpper = isUpper;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
