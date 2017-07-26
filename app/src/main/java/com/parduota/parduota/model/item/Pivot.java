
package com.parduota.parduota.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pivot {

    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
