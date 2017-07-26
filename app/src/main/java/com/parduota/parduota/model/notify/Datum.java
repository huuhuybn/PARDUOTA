
package com.parduota.parduota.model.notify;

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
    private Integer readed;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("type_desc")
    @Expose
    private String typeDesc;

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

    public Integer getReaded() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

}
