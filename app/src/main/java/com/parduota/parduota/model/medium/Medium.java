
package com.parduota.parduota.model.medium;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Medium {

    @SerializedName("medium")
    @Expose
    private Medium_ medium;

    public Medium_ getMedium() {
        return medium;
    }

    public void setMedium(Medium_ medium) {
        this.medium = medium;
    }

}
