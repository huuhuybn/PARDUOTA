package com.parduota.parduota.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MAC2015 on 11/26/17.
 */

public class Password {


    @SerializedName("status")
    @Expose
    private
    String status;

    @Expose
    @SerializedName("messages")
    private
    String messages;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
