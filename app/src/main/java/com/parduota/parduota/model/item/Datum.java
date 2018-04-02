
package com.parduota.parduota.model.item;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {


    private String statusText;
    private int colorStatus;

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public int getColorStatus() {
        return colorStatus;
    }

    public void setColorStatus(int colorStatus) {
        this.colorStatus = colorStatus;
    }

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
    private Integer quantity;
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
    private Integer condition;
    @SerializedName("ebay_id")
    @Expose
    private Object ebayId;
    @SerializedName("ebay_timestamp")
    @Expose
    private Object ebayTimestamp;
    @SerializedName("pay_status")
    @Expose
    private double payStatus;
    @SerializedName("weight")
    @Expose
    private double weight;
    @SerializedName("length")
    @Expose
    private double length;
    @SerializedName("width")
    @Expose
    private double width;
    @SerializedName("height")
    @Expose
    private double height;
    @SerializedName("shipping_type")
    @Expose
    private String shippingType;
    @SerializedName("shipping_service")
    @Expose
    private String shippingService;
    @SerializedName("shipping_service_cost")
    @Expose
    private double shippingServiceCost;
    @SerializedName("shipping_service_add_cost")
    @Expose
    private double shippingServiceAddCost;
    @SerializedName("sell_for_charity")
    @Expose
    private Integer sellForCharity;
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
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("user")
    @Expose
    private List<User> user = null;
    @SerializedName("media")
    @Expose
    private List<Medium> media = null;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public void setEbayId(Object ebayId) {
        this.ebayId = ebayId;
    }

    public void setEbayTimestamp(Object ebayTimestamp) {
        this.ebayTimestamp = ebayTimestamp;
    }

    public void setPayStatus(double payStatus) {
        this.payStatus = payStatus;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public void setShippingService(String shippingService) {
        this.shippingService = shippingService;
    }

    public void setShippingServiceCost(double shippingServiceCost) {
        this.shippingServiceCost = shippingServiceCost;
    }

    public void setShippingServiceAddCost(double shippingServiceAddCost) {
        this.shippingServiceAddCost = shippingServiceAddCost;
    }

    public void setSellForCharity(Integer sellForCharity) {
        this.sellForCharity = sellForCharity;
    }

    public void setExtraFees(Integer extraFees) {
        this.extraFees = extraFees;
    }

    public void setOtherShipping(Integer otherShipping) {
        this.otherShipping = otherShipping;
    }

    public void setShippingTypeCustom(String shippingTypeCustom) {
        this.shippingTypeCustom = shippingTypeCustom;
    }

    public void setEndItem(Integer endItem) {
        this.endItem = endItem;
    }

    public void setBuyerInfo(String buyerInfo) {
        this.buyerInfo = buyerInfo;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setEbayPrice(String ebayPrice) {
        this.ebayPrice = ebayPrice;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getStatus() {
        return status;
    }

    public String getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Integer getCondition() {
        return condition;
    }

    public Object getEbayId() {
        return ebayId;
    }

    public Object getEbayTimestamp() {
        return ebayTimestamp;
    }

    public double getPayStatus() {
        return payStatus;
    }

    public double getWeight() {
        return weight;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getShippingType() {
        return shippingType;
    }

    public String getShippingService() {
        return shippingService;
    }

    public double getShippingServiceCost() {
        return shippingServiceCost;
    }

    public double getShippingServiceAddCost() {
        return shippingServiceAddCost;
    }

    public Integer getSellForCharity() {
        return sellForCharity;
    }

    public Integer getExtraFees() {
        return extraFees;
    }

    public Integer getOtherShipping() {
        return otherShipping;
    }

    public String getShippingTypeCustom() {
        return shippingTypeCustom;
    }

    public Integer getEndItem() {
        return endItem;
    }

    public String getBuyerInfo() {
        return buyerInfo;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getEbayPrice() {
        return ebayPrice;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<User> getUser() {
        return user;
    }

    public List<Medium> getMedia() {
        return media;
    }
}
