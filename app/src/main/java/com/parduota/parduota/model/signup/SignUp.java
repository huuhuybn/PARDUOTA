
package com.parduota.parduota.model.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SignUp {


    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("error")
    @Expose
    private Error error;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
