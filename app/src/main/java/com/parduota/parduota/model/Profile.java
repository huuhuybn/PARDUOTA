package com.parduota.parduota.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MAC2015 on 11/30/17.
 */

class Profile {

    @SerializedName("bank_account")
    @Expose
    private
    String bank_account;

    @SerializedName("gender")
    @Expose
    private
    String gender;

    @SerializedName("full_name")
    @Expose
    private
    String full_name;

    @SerializedName("company")
    @Expose
    private
    String company;


    @SerializedName("address")
    @Expose
    private
    String address;

    @SerializedName("phone")
    @Expose
    private
    String phone;

    @SerializedName("description")
    @Expose
    private
    String description;


    @SerializedName("other")
    @Expose
    private
    String other;


    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
