
package com.parduota.parduota.model.updateitem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Item {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("pay_status")
    @Expose
    private Integer payStatus;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("shipping_type")
    @Expose
    private String shippingType;
    @SerializedName("shipping_service")
    @Expose
    private String shippingService;
    @SerializedName("shipping_service_cost")
    @Expose
    private Integer shippingServiceCost;
    @SerializedName("shipping_service_add_cost")
    @Expose
    private Integer shippingServiceAddCost;
    @SerializedName("sell_for_charity")
    @Expose
    private String sellForCharity;
    @SerializedName("extra_fees")
    @Expose
    private Integer extraFees;
    @SerializedName("other_shipping")
    @Expose
    private Integer otherShipping;
    @SerializedName("shipping_type_custom")
    @Expose
    private String shippingTypeCustom;
    @SerializedName("end_item")
    @Expose
    private Integer endItem;
    @SerializedName("buyer_info")
    @Expose
    private String buyerInfo;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("ebay_price")
    @Expose
    private String ebayPrice;
    @SerializedName("margin")
    @Expose
    private Integer margin;
    @SerializedName("language")
    @Expose
    private String language;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getShippingService() {
        return shippingService;
    }

    public void setShippingService(String shippingService) {
        this.shippingService = shippingService;
    }

    public Integer getShippingServiceCost() {
        return shippingServiceCost;
    }

    public void setShippingServiceCost(Integer shippingServiceCost) {
        this.shippingServiceCost = shippingServiceCost;
    }

    public Integer getShippingServiceAddCost() {
        return shippingServiceAddCost;
    }

    public void setShippingServiceAddCost(Integer shippingServiceAddCost) {
        this.shippingServiceAddCost = shippingServiceAddCost;
    }

    public String getSellForCharity() {
        return sellForCharity;
    }

    public void setSellForCharity(String sellForCharity) {
        this.sellForCharity = sellForCharity;
    }

    public Integer getExtraFees() {
        return extraFees;
    }

    public void setExtraFees(Integer extraFees) {
        this.extraFees = extraFees;
    }

    public Integer getOtherShipping() {
        return otherShipping;
    }

    public void setOtherShipping(Integer otherShipping) {
        this.otherShipping = otherShipping;
    }

    public String getShippingTypeCustom() {
        return shippingTypeCustom;
    }

    public void setShippingTypeCustom(String shippingTypeCustom) {
        this.shippingTypeCustom = shippingTypeCustom;
    }

    public Integer getEndItem() {
        return endItem;
    }

    public void setEndItem(Integer endItem) {
        this.endItem = endItem;
    }

    public String getBuyerInfo() {
        return buyerInfo;
    }

    public void setBuyerInfo(String buyerInfo) {
        this.buyerInfo = buyerInfo;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getEbayPrice() {
        return ebayPrice;
    }

    public void setEbayPrice(String ebayPrice) {
        this.ebayPrice = ebayPrice;
    }

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
