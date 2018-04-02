package com.parduota.parduota.ion;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedHashTreeMap;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huy_quynh on 6/19/17.
 */

public class ION implements Constant {


    public static void getData(Context context, String url, Class class_, FutureCallback futureCallback) {
        Ion.with(context)
                .load(url)
                .as(class_)
                .setCallback(futureCallback);
    }

    public static void postData(Context context, String url, Map<String, List<String>> parameters, Class class_
            , FutureCallback futureCallback) {
        Ion.with(context)
                .load(url).setBodyParameters(parameters)
                .as(class_)
                .setCallback(futureCallback);
    }

    public static void uploadFile(Context context, String token, String url, File file
            , FutureCallback futureCallback) {
        Ion.with(context)
                .load(url).setHeader("Authorization", "Bearer" + " " + token).setMultipartFile(MEDIA, file)
                .asString()
                .setCallback(futureCallback);
    }


    public static void getDataWithToken(Context context, String token, String url, Class class_
            , FutureCallback futureCallback) {
        if (Constant.isDEBUG)
            Log.e("getDataWithToken", url);
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


    public static Map<String, List<String>> updateCredit(String payID) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(PAYMENT_ID, Arrays.asList(payID));
        return params;
    }

    public static Map<String, List<String>> loginForm(String email, String password) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(EMAIL, Arrays.asList(email));
        params.put(PASSWORD, Arrays.asList(password));
        return params;
    }

    public static Map<String, List<String>> fcmUpdate(String fcm) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(TOKEN_FCM, Arrays.asList(fcm));
        params.put(TYPE, Arrays.asList(ANDROID));
        return params;
    }

    public static Map<String, List<String>> loginFacebook(String email, String name, String fb_id) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(EMAIL, Arrays.asList(email));
        params.put(NAME, Arrays.asList(name));
        params.put(PROVIDER_ID, Arrays.asList(fb_id));
        params.put(PROVIDER, Arrays.asList("facebook"));
        Log.e("PARAM", params.toString());
        return params;
    }

    public static Map<String, List<String>> signUP(String email, String name, String password) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(EMAIL, Arrays.asList(email));
        params.put(NAME, Arrays.asList(name));
        params.put(PASSWORD, Arrays.asList(password));
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
        params.put(BANK_ACCOUNT, Arrays.asList(bank_account));
        params.put(GENDER, Arrays.asList(gender));
        params.put(FULL_NAME, Arrays.asList(full_name));
        params.put(PHONE,Arrays.asList(phone));
        params.put(COMPANY, Arrays.asList(company));
        params.put(ADDRESS, Arrays.asList(address));
        params.put(DESCRIPTION, Arrays.asList(description));
        params.put(OTHER, Arrays.asList(other));

        Log.e("PARAM", params.toString());
        return params;
    }

    public static Map<String, List<String>> changePass(String oldPass, String newPass, String etNewPassCon) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(OLD_PASSWORD, Arrays.asList(oldPass));
        params.put(NEW_PASSWORD, Arrays.asList(newPass));
        params.put(NEW_PASSWORD_CONFIRM, Arrays.asList(etNewPassCon));
        Log.e("PARAM", params.toString());
        return params;
    }

}
