
package com.parduota.parduota.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("verified")
    @Expose
    private Integer verified;
    @SerializedName("email_token")
    @Expose
    private Object emailToken;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("provider_id")
    @Expose
    private String providerId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("profile_url")
    @Expose
    private String profileUrl;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("viber")
    @Expose
    private String viber;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("other")
    @Expose
    private Object other;
    @SerializedName("parent_user")
    @Expose
    private Integer parentUser;
    @SerializedName("vip")
    @Expose
    private Integer vip;
    @SerializedName("block")
    @Expose
    private Integer block;
    @SerializedName("credit")
    @Expose
    private String credit;
    @SerializedName("bank_account")
    @Expose
    private Object bankAccount;
    @SerializedName("token_FCM")
    @Expose
    private Object tokenFCM;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public Object getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(Object emailToken) {
        this.emailToken = emailToken;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getViber() {
        return viber;
    }

    public void setViber(String viber) {
        this.viber = viber;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }

    public Integer getParentUser() {
        return parentUser;
    }

    public void setParentUser(Integer parentUser) {
        this.parentUser = parentUser;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Integer getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Object getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(Object bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Object getTokenFCM() {
        return tokenFCM;
    }

    public void setTokenFCM(Object tokenFCM) {
        this.tokenFCM = tokenFCM;
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

}
