
package com.parduota.parduota.model.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cat {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("CategoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("CategoryParentID")
    @Expose
    private Integer categoryParentID;
    @SerializedName("CategoryLevel")
    @Expose
    private Integer categoryLevel;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("pivot")
    @Expose
    private Pivot pivot;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getCategoryParentID() {
        return categoryParentID;
    }

    public void setCategoryParentID(Integer categoryParentID) {
        this.categoryParentID = categoryParentID;
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

}
