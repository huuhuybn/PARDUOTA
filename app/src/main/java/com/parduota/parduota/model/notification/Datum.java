
package com.parduota.parduota.model.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("for")
    @Expose
    private Integer _for;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("meta_data")
    @Expose
    private MetaData metaData;
    @SerializedName("readed")
    @Expose
    private int readed;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("vip")
    @Expose
    private Integer vip;
    @SerializedName("by")
    @Expose
    private Integer by;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("type_desc")
    @Expose
    private String typeDesc;
    @SerializedName("by_user")
    @Expose
    private Object byUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFor() {
        return _for;
    }

    public void setFor(Integer _for) {
        this._for = _for;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public int getReaded() {
        return readed;
    }

    public void setReaded(Integer readed) {
        this.readed = readed;
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

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Integer getBy() {
        return by;
    }

    public void setBy(Integer by) {
        this.by = by;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public Object getByUser() {
        return byUser;
    }

    public void setByUser(Object byUser) {
        this.byUser = byUser;
    }

}
