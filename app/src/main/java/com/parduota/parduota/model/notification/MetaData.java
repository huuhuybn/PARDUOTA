
package com.parduota.parduota.model.notification;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ebay_id")
    @Expose
    private String ebayId;
    @SerializedName("notice")
    @Expose
    private String notice;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
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
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("ebay_timestamp")
    @Expose
    private Object ebayTimestamp;
    @SerializedName("pay_status")
    @Expose
    private Integer payStatus;
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
    private String shippingServiceCost;
    @SerializedName("shipping_service_add_cost")
    @Expose
    private String shippingServiceAddCost;
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
    @SerializedName("cat")
    @Expose
    private List<Cat> cat = null;
    @SerializedName("media")
    @Expose
    private List<Medium> media = null;
    @SerializedName("user")
    @Expose
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEbayId() {
        return ebayId;
    }

    public void setEbayId(String ebayId) {
        this.ebayId = ebayId;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Object getEbayTimestamp() {
        return ebayTimestamp;
    }

    public void setEbayTimestamp(Object ebayTimestamp) {
        this.ebayTimestamp = ebayTimestamp;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
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

    public String getShippingServiceCost() {
        return shippingServiceCost;
    }

    public void setShippingServiceCost(String shippingServiceCost) {
        this.shippingServiceCost = shippingServiceCost;
    }

    public String getShippingServiceAddCost() {
        return shippingServiceAddCost;
    }

    public void setShippingServiceAddCost(String shippingServiceAddCost) {
        this.shippingServiceAddCost = shippingServiceAddCost;
    }

    public Integer getSellForCharity() {
        return sellForCharity;
    }

    public void setSellForCharity(Integer sellForCharity) {
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

    public List<Cat> getCat() {
        return cat;
    }

    public void setCat(List<Cat> cat) {
        this.cat = cat;
    }

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
