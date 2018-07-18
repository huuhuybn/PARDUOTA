package com.parduota.parduota.remote;

import com.google.gson.JsonObject;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.Login;
import com.parduota.parduota.model.MessageResponse;
import com.parduota.parduota.model.UploadItem;
import com.parduota.parduota.model.charger.Charge;
import com.parduota.parduota.model.createorder.OrderResponse;
import com.parduota.parduota.model.item.Item;
import com.parduota.parduota.model.notification.Notification;
import com.parduota.parduota.model.order.Order;
import com.parduota.parduota.model.updateitem.ItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitRequest {

    String AUTHORIZATION = "Authorization";

    String PRE_TOKEN = "Bearer ";


    @Headers({"Content-Type: multipart/form-data"})
    @POST("api/auth/login")
    Call<JsonObject> loginViaEmail(@Query("email") String user, @Query("password") String password);


    @POST("api/auth/login_social")
    Call<Login> loginWithFacebook(@Query("name") String name, @Query("provider_id") String providerID, @Query("provider") String provider);


    @POST("api/item/edit/{id}")
    Call<JsonObject> updateItem(String id, UploadItem uploadItem);


    @GET("api/get_notification")
    Call<Notification> getNotification(@Header(AUTHORIZATION) String token, @Query("page") int page, @Query("locale") String locale);


    @GET("api/orders/ping_messages/{order_id}")
    Call<List<MessageResponse>> getOrderMessage(@Header(AUTHORIZATION) String token, @Path("order_id") int order_id);


    @POST("api/orders/add_messages/{id}")
    Call<JsonObject> sendOrderMessage(@Header(AUTHORIZATION) String token, @Path("id") int id, @Query("message") String message);

    @GET("api/get_setting?type=term_en")
    Call<JsonObject> getTermAndCondition();

    @POST("api/term_accept")
    Call<JsonObject> acceptTermAndCondition(@Header(AUTHORIZATION) String token);


    @GET("api/get_orders")
    Call<Order> getOrderList(@Query("page") int page, @Header(AUTHORIZATION) String token);


    @POST("api/orders/add")
    Call<OrderResponse> addNewOrder(@Header(AUTHORIZATION) String token, @Query(Constant.TITLE) String title, @Query(Constant.EBAY_ID) String ebay_id, @Query(Constant.NOTICE) String notice);


    @GET("api/get_item_by_status/{type}")
    Call<Item> getItemByType(@Header(AUTHORIZATION) String token, @Path("type") int type, @Query("page") int page);

    @GET("api/history_charges")
    Call<Charge> getChargeList(@Header(AUTHORIZATION) String token, @Query("page") int page);

    @GET("/api/get_item/{id}")
    Call<ItemResponse> getItemDetail(@Header(AUTHORIZATION) String token,@Path("id") int itemID);
}
