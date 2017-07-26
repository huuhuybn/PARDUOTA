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


    public static void postDataWithToken(Context context, String url, String token, Map<String, List<String>> parameters, Class class_
            , FutureCallback futureCallback) {
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
}
