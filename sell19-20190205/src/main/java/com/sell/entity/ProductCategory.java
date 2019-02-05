package com.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//类目
@Entity
@Data
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    public Integer getCategoryId() {
        return categoryId;
    }

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer getCategoryType) {
        this.categoryName = categoryName;
        this.categoryType = getCategoryType;
    }

}
