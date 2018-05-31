package com.parduota.parduota.remote;

import com.google.gson.JsonObject;
import com.parduota.parduota.model.Login;
import com.parduota.parduota.model.UploadItem;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ParduotaRequest {

    @POST("api/auth/login")
    Call<ResponseBody> loginViaEmail(@Query("email") String user, @Query("password") String password);

    @POST("api/auth/login_social")
    Call<Login> loginWithFacebook(@Query("name") String name, @Query("provider_id") String providerID, @Query("provider") String provider);


    @POST("api/item/edit/{id}")
    Call<JsonObject> updateItem(String id, UploadItem uploadItem);

}
