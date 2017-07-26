
package com.parduota.parduota.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pivot_ {

    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("media_id")
    @Expose
    private Integer mediaId;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

}
