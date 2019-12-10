package com.zmx.test;

import com.alibaba.excel.annotation.ExcelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : 钟鸣星
 * @date : 2019年12月06日
 */
public class ProductEntity {
    @ExcelProperty("序列号")
    private Integer id;
    @ExcelProperty("产品类型ID")
    private Integer typeId;
    @ExcelProperty("品牌ID")
    private Integer brandId;
    @ExcelProperty("产品名称")
    private String name;
    @ExcelProperty("产品价格")
    private BigDecimal price;
    @ExcelProperty("是否上架")
    private Byte isUpper;
    @ExcelProperty("是否有效")
    private Byte isActive;
    @ExcelProperty("是否删除")
    private Byte isDeleted;
    @ExcelProperty("创建时间")
    private Date createTime;
    @ExcelProperty("更新时间")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
