
package com.parduota.parduota.model.updateitem;

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
    private String description;
    @SerializedName("other")
    @Expose
    private String other;
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
    private String bankAccount;
    @SerializedName("token_FCM")
    @Expose
    private Object tokenFCM;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("ebay_approved")
    @Expose
    private Integer ebayApproved;
    @SerializedName("kick_out")
    @Expose
    private Integer kickOut;
    @SerializedName("expiry_date")
    @Expose
    private String expiryDate;
    @SerializedName("term_accept")
    @Expose
    private Integer termAccept;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("registered_number")
    @Expose
    private String registeredNumber;
    @SerializedName("company_address")
    @Expose
    private String companyAddress;
    @SerializedName("company_zip_code")
    @Expose
    private String companyZipCode;
    @SerializedName("company_country")
    @Expose
    private String companyCountry;
    @SerializedName("p_name")
    @Expose
    private String pName;
    @SerializedName("p_surname")
    @Expose
    private String pSurname;
    @SerializedName("p_phone")
    @Expose
    private String pPhone;
    @SerializedName("business")
    @Expose
    private Integer business;
    @SerializedName("expiry_ebay")
    @Expose
    private Integer expiryEbay;

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

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
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

    public Integer getEbayApproved() {
        return ebayApproved;
    }

    public void setEbayApproved(Integer ebayApproved) {
        this.ebayApproved = ebayApproved;
    }

    public Integer getKickOut() {
        return kickOut;
    }

    public void setKickOut(Integer kickOut) {
        this.kickOut = kickOut;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getTermAccept() {
        return termAccept;
    }

    public void setTermAccept(Integer termAccept) {
        this.termAccept = termAccept;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getRegisteredNumber() {
        return registeredNumber;
    }

    public void setRegisteredNumber(String registeredNumber) {
        this.registeredNumber = registeredNumber;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyZipCode() {
        return companyZipCode;
    }

    public void setCompanyZipCode(String companyZipCode) {
        this.companyZipCode = companyZipCode;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPSurname() {
        return pSurname;
    }

    public void setPSurname(String pSurname) {
        this.pSurname = pSurname;
    }

    public String getPPhone() {
        return pPhone;
    }

    public void setPPhone(String pPhone) {
        this.pPhone = pPhone;
    }

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public Integer getExpiryEbay() {
        return expiryEbay;
    }

    public void setExpiryEbay(Integer expiryEbay) {
        this.expiryEbay = expiryEbay;
    }

}
