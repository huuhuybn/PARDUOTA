package com.parduota.parduota.model;

import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

/**
 * Created by MAC2015 on 11/9/17.
 */

public class UploadItem {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String title = "";
    private String description = "";
    private String price = "";
    private String location = "";
    private String country = "";
    private String quantity = "";
    private String media = "";
    private String condition = "";
    private String weight = "";
    private String length = "";
    private String width = "";
    private String height = "";
    private String shipping_type = "";

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    private boolean isUpdate;

    private ArrayList<AlbumFile> photoArr = new ArrayList<>();

    public ArrayList<AlbumFile> getPhotoArr() {
        return photoArr;
    }

    public void setPhotoArr(ArrayList<AlbumFile> photoArr) {
        this.photoArr = photoArr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getShipping_type() {
        return shipping_type;
    }

    public void setShipping_type(String shipping_type) {
        this.shipping_type = shipping_type;
    }

    public String getShipping_service() {
        return shipping_service;
    }

    public void setShipping_service(String shipping_service) {
        this.shipping_service = shipping_service;
    }

    public String getShipping_service_cost() {
        return shipping_service_cost;
    }

    public void setShipping_service_cost(String shipping_service_cost) {
        this.shipping_service_cost = shipping_service_cost;
    }

    public String getShipping_service_add_cost() {
        return shipping_service_add_cost;
    }

    public void setShipping_service_add_cost(String shipping_service_add_cost) {
        this.shipping_service_add_cost = shipping_service_add_cost;
    }

    public String getSell_for_charity() {
        return sell_for_charity;
    }

    public void setSell_for_charity(String sell_for_charity) {
        this.sell_for_charity = sell_for_charity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShipping_type_custom() {
        return shipping_type_custom;
    }

    public void setShipping_type_custom(String shipping_type_custom) {
        this.shipping_type_custom = shipping_type_custom;
    }

    private String shipping_service = "";
    private String shipping_service_cost = "";
    private String shipping_service_add_cost = "";
    private String sell_for_charity = "";
    private String status = "";
    private String shipping_type_custom = "";
}
