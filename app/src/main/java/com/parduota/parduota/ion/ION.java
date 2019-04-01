package com.parduota.parduota.ion;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.BasicNameValuePair;
import com.koushikdutta.async.http.NameValuePair;
import com.koushikdutta.ion.Ion;
import com.parduota.parduota.model.UploadItem;
import com.yanzhenjie.album.AlbumFile;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huy_quynh on 6/19/17.
 */

public class ION implements Constant {


    public static NameValuePair authHeader(String token) {
        return new BasicNameValuePair("Authorization", "Bearer " + token);

    }


    public static void getData(Context context, String url, Class class_, FutureCallback futureCallback) {
        Ion.with(context)
                .load(url).setHeader()
                .as(class_)
                .setCallback(futureCallback);
    }

    public static void postData(Context context, String url, Map<String, List<String>> parameters, Class class_
            , FutureCallback futureCallback) {
        Ion.with(context)
                .load(url).setMultipartParameters(parameters)
                .as(class_)
                .setCallback(futureCallback);
    }

    public static void uploadFile(Context context, ProgressBar progressBar, String token, String url, File file
            , FutureCallback<JsonObject> futureCallback) {
        Ion.with(context)
                .load(url).setHeader("Authorization", "Bearer" + " " + token).progressBar(progressBar).setMultipartFile(MEDIA, file)
                .asJsonObject()
                .setCallback(futureCallback);
    }


    public static <T> void getDataWithToken(Context context, String token, String url, Class class_
            , FutureCallback<T> futureCallback) {
        if (Constant.isDEBUG)
            Log.e("getDataWithToken", url);

        // when new user login,
        if (token != null)
            Ion.with(context)
                    .load(url).setHeader("Authorization", "Bearer" + " " + token.trim())
                    .as(class_)
                    .setCallback(futureCallback);
    }

    public static void postDataWithToken(Context context, String url, String token, Map<String, List<String>> parameters, Class class_
            , FutureCallback futureCallback) {
        if (parameters == null) {
            Ion.with(context)
                    .load(url).setHeader("Authorization", "Bearer" + " " + token)
                    .as(class_)
                    .setCallback(futureCallback);
        } else
            Ion.with(context)
                    .load(url).setHeader("Authorization", "Bearer" + " " + token).setBodyParameters(parameters)
                    .as(class_)
                    .setCallback(futureCallback);
    }

    public static void postFormDataWithToken(Context context, String url, String token, Map<String, List<String>> parameters
            , FutureCallback futureCallback) {
        if (parameters == null) {
            Ion.with(context)
                    .load(url).setHeader("Authorization", "Bearer" + " " + token)
                    .asJsonObject()
                    .setCallback(futureCallback);
        } else
            Ion.with(context)
                    .load(url).setHeader("Authorization", "Bearer" + " " + token).setMultipartParameters(parameters)
                    .asJsonObject()
                    .setCallback(futureCallback);
    }


    public static Map<String, List<String>> updateCredit(String payID) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(PAYMENT_ID, Collections.singletonList(payID));
        return params;
    }

    public static Map<String, List<String>> loginForm(String email, String password) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(EMAIL, Collections.singletonList(email));
        params.put(PASSWORD, Collections.singletonList(password));
        return params;
    }

    public static Map<String, List<String>> fcmUpdate(String fcm) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(TOKEN_FCM, Collections.singletonList(fcm));
        params.put(TYPE, Collections.singletonList(ANDROID));
        return params;
    }

    public static Map<String, List<String>> loginFacebook(String email, String name, String fb_id) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        //params.put(EMAIL, Arrays.asList(email));
        params.put(NAME, Collections.singletonList(name));
        params.put(PROVIDER_ID, Collections.singletonList(fb_id));
        params.put(PROVIDER, Collections.singletonList("facebook"));
        Log.e("PARAM", params.toString());
        return params;
    }

    public static Map<String, List<String>> signUP(String email, String name, String password) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(EMAIL, Collections.singletonList(email));
        params.put(NAME, Collections.singletonList(name));
        params.put(PASSWORD, Collections.singletonList(password));
        Log.e("PARAM", params.toString());
        return params;
    }

    //
//    bank_account:123123
//    gender:male
//    full_name:Trần Xuân Đức
//    company:
//    address:
//    phone:
//    description:
//    other:

    public static Map<String, List<String>> updateProfile(String bank_account, String gender, String full_name, String company, String address, String phone, String description, String other) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(BANK_ACCOUNT, Collections.singletonList(bank_account));
        params.put(GENDER, Collections.singletonList(gender));
        params.put(FULL_NAME, Collections.singletonList(full_name));
        params.put(PHONE, Collections.singletonList(phone));
        params.put(COMPANY, Collections.singletonList(company));
        params.put(ADDRESS, Collections.singletonList(address));
        params.put(DESCRIPTION, Collections.singletonList(description));
        params.put(OTHER, Collections.singletonList(other));

        Log.e("PARAM", params.toString());
        return params;
    }

    public static Map<String, List<String>> changePass(String oldPass, String newPass, String etNewPassCon) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(OLD_PASSWORD, Collections.singletonList(oldPass));
        params.put(NEW_PASSWORD, Collections.singletonList(newPass));
        params.put(NEW_PASSWORD_CONFIRM, Collections.singletonList(etNewPassCon));
        Log.e("PARAM", params.toString());
        return params;
    }


    public static Map<String, List<String>> createOrder(String title, String ebay_id, String des) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(TITLE, Collections.singletonList(title));
        params.put(EBAY_ID, Collections.singletonList(ebay_id));
        params.put(NOTICE, Collections.singletonList(des));
        Log.e("PARAM", params.toString());
        return params;
    }


    public static Map<String, List<String>> createMessage(String message) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(MESSAGE, Collections.singletonList(message));
        Log.e("PARAM", params.toString());
        return params;
    }

//    full_name:Tên đầy đủ
//    address:Địa chỉ
//    phone:0978281836
//    bank_account:121313
//    gender:male
//    country:VN
//    zip_code:
//    business:off
//    company:
//    company_zip_code:
//    registered_number:
//    company_address:
//    company_zip_code:
//    company_country:
//    p_name:
//    p_surname:
//    p_phone:
//    other:

    public static Map<String, List<String>> getVerify(String full_name, String address, String phone, String bank_account, String gender, String country, String zip_code, String business, String company, String company_zip_code, String registered_number, String company_address, String company_country, String p_name, String p_surname, String p_phone, String other) {

        Map<String, List<String>> params = new HashMap<>();

        params.put(FULL_NAME, Collections.singletonList(full_name));
        params.put(ADDRESS, Collections.singletonList(address));
        params.put(PHONE, Collections.singletonList(phone));
        params.put(BANK_ACCOUNT, Collections.singletonList(bank_account));
        params.put(GENDER, Collections.singletonList(gender));
        params.put(COUNTRY, Collections.singletonList(country));
        params.put(ZIP_CODE, Collections.singletonList(zip_code));
        params.put(BUSINESS, Collections.singletonList(business));

        if (business.equals("on")) {
            params.put(COMPANY, Collections.singletonList(company));
            params.put(COMPANY_ZIP_CODE, Collections.singletonList(company_zip_code));
            params.put(REGISTERED_NUMBER, Collections.singletonList(registered_number));
            params.put(COMPANY_ADDRESS, Collections.singletonList(company_address));
            params.put(COMPANY_ZIP_CODE, Collections.singletonList(company_zip_code));
            params.put(COMPANY_COUNTRY, Collections.singletonList(company_country));
            params.put(P_NAME, Collections.singletonList(p_name));
            params.put(P_SURNAME, Collections.singletonList(p_surname));
            params.put(P_PHONE, Collections.singletonList(p_phone));
            params.put(OTHER, Collections.singletonList(other));
        }

        Log.e("PARAM", params.toString());

        return params;
    }


    public static Map<String, List<String>> addItem(UploadItem uploadItem) {

        Map<String, List<String>> params = new HashMap<String, List<String>>();

        params.put(TITLE, Collections.singletonList(uploadItem.getTitle()));

        params.put(DESCRIPTION, Collections.singletonList(uploadItem.getDescription()));

        params.put(PRICE, Collections.singletonList(uploadItem.getPrice()));

        params.put(LOCATION, Collections.singletonList(uploadItem.getLocation()));

        params.put(COUNTRY, Collections.singletonList(uploadItem.getCountry()));

        params.put(QUANTITY, Collections.singletonList(uploadItem.getQuantity()));

        try {

            ArrayList<AlbumFile> files = uploadItem.getPhotoArr();

            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                arrayList.add(files.get(i).getMediaType() + "");
            }

            String arrPhoto = arrayList.toString().trim();

            if (Constant.isDEBUG) Log.e("AAA",arrPhoto);
            arrPhoto = arrPhoto.substring(1, arrPhoto.length() - 1);

            if (Constant.isDEBUG) Log.e("AAA",arrPhoto);
            arrPhoto = arrPhoto.replaceAll(" ", "");
            params.put(MEDIA, Collections.singletonList(arrPhoto));

            if (Constant.isDEBUG)
            Log.e("VVV", arrPhoto);

        } catch (Exception e) {


        }
        params.put(CONDITION, Collections.singletonList(uploadItem.getCondition()));

        params.put(WEIGHT, Collections.singletonList(uploadItem.getWeight()));

        params.put(LENGTH, Collections.singletonList(uploadItem.getLength()));

        params.put(WIDTH, Collections.singletonList(uploadItem.getWidth()));

        params.put(HEIGHT, Collections.singletonList(uploadItem.getHeight()));

        params.put(SHIPPING_TYPE, Collections.singletonList(uploadItem.getShipping_type()));

        params.put(SHIPPING_SERVICE, Collections.singletonList(uploadItem.getShipping_service()));

        params.put(SHIPPING_SERVICE_COST, Collections.singletonList(uploadItem.getShipping_service_cost()));

        params.put(SHIPPING_SERVICE_ADD_COST, Collections.singletonList(uploadItem.getShipping_service_add_cost()));

        params.put(SELL_FOR_CHARITY, Collections.singletonList(uploadItem.getSell_for_charity()));

        params.put(STATUS, Collections.singletonList(uploadItem.getStatus()));

        params.put(SHIPPING_TYPE_CUSTOM, Collections.singletonList(uploadItem.getShipping_type()));

        if (Constant.isDEBUG)
            Log.e("PARAM", params.toString());
        return params;
    }

    public static Map<String, List<String>> addCredit(JSONObject data) {

        Map<String, List<String>> params = new HashMap<String, List<String>>();

        try {

            JSONObject response = data.getJSONObject("response");
            String id = response.getString("id");
            params.put(PAYMENT_ID, Collections.singletonList(id));
        } catch (Exception ignored) {

        }

        Log.e("PARAM", params.toString());
        return params;
    }
}
