package com.sell.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//类目
@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;

    public Integer getCategoryId() {
        return categoryId;
    }

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer getCategoryType) {
        this.categoryName = categoryName;
        this.categoryType = getCategoryType;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getGetCategoryType() {
        return categoryType;
    }

    public void setGetCategoryType(Integer getCategoryType) {
        this.categoryType = getCategoryType;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", getCategoryType=" + categoryType +
                '}';
    }
}
