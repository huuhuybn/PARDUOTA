package com.parduota.parduota.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;


import com.google.gson.Gson;
import com.parduota.parduota.model.User;

import java.util.Locale;
import java.util.Set;

/**
 * Created by MAC2015 on 10/21/16.
 */

public class SharePrefManager {

    private static final String PREFERENCES_NAME = "PREFERENCES_NAME";
    private static final String IS_FACEBOOK_LOGIN = "IS_FACEBOOK_LOGIN";
    private final String ACCESS_TOKEN = "_access_token_123";
    private final String USER = "user___";

    private final String VERIFY = "verify__";
    private final String ACCOUNT_LOGIN = "login___";
    private final String FCM = "FCM__";


    private final String COUNT_NOTI = "count_noti";


    private static SharePrefManager instance = null;
    private Context context;

    /**
     * Constructor
     */
    private SharePrefManager() {

    }

    /**
     * Get class instance
     *
     * @param context
     * @return
     */
    public static SharePrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharePrefManager();
            instance.context = context;
        }
        return instance;
    }


    public void saveUser(User user) {
        putStringValue(USER, new Gson().toJson(user));
    }

    public User getUser() {
        String json = getStringValue(USER);
        return new Gson().fromJson(json, User.class);
    }


    public int getCountNotification() {
        return getIntValue(COUNT_NOTI, 0);
    }

    public void setCountNotification(int count) {
        putIntValue(COUNT_NOTI, count);
    }

    public void subCountNotification() {
        int count = getCountNotification();
        if (count > 0) count = count - 1;
        setCountNotification(count);
    }

    public void addCountNoti() {
        int count = getCountNotification();
        count = count + 1;
        setCountNotification(count);
    }

    public void saveAccessToken(String access_token) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(ACCESS_TOKEN, access_token);
        editor.apply();
    }

    public String getAccessToken() {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                0);
        return pref.getString(ACCESS_TOKEN, null);
    }

    public void clearAccessToken() {
        String WAS_LOGIN = "_was_log_in_123";
        putBooleanValue(WAS_LOGIN, false);
        putStringValue(ACCESS_TOKEN, null);
    }

    /**
     * Save an string to SharedPreferences
     *
     * @param key
     * @param s
     */
    private void putStringValue(String key, String s) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, s);
        editor.apply();
    }

    /**
     * Read an string to SharedPreferences
     *
     * @param key
     * @return
     */
    private String getStringValue(String key) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                0);
        return pref.getString(key, "");
    }

    /**
     * Read an string to SharedPreferences
     *
     * @param key
     * @param defaultValue
     * @return
     */
    private String getStringValue(String key, String defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        return pref.getString(key, defaultValue);
    }

    /**
     * Save an boolean to SharedPreferences
     *
     * @param key
     */
    private void putBooleanValue(String key, Boolean b) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    /**
     * Read an boolean to SharedPreferences
     *
     * @param key
     * @return
     */
    public boolean getBooleanValue(String key) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    /**
     * Read an boolean to SharedPreferences
     *
     * @param key
     * @return
     */
    public boolean getBooleanValue(String key, boolean defaulValue) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                0);
        return pref.getBoolean(key, defaulValue);
    }

    /**
     * Save an float to SharedPreferences
     *
     * @param key
     */
    public void putFloatValue(String key, float f) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, f);
        editor.apply();
    }

    /**
     * Read an float to SharedPreferences
     *
     * @param key
     * @return
     */
    public float getFloatValue(String key) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                0);
        return pref.getFloat(key, 0.0f);
    }

    /**
     * Save an int to SharedPreferences
     *
     * @param key
     * @param value
     */
    private void putIntValue(String key, int value) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Read an int to SharedPreferences
     *
     * @param key
     * @return
     */
    public int getIntValue(String key) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    /**
     * Read an int to SharedPreferences
     *
     * @param key
     * @return
     */
    private int getIntValue(String key, int defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        return pref.getInt(key, defaultValue);
    }

    /**
     * Save an long to SharedPreferences
     *
     * @param key
     * @param value
     */
    public void putLongValue(String key, long value) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Read an long to SharedPreferences
     *
     * @param key
     * @return
     */
    public long getLongValue(String key) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                0);
        return pref.getLong(key, 0);
    }

    public void putStringSet(String key, Set<String> listString) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet(key, listString);
        editor.apply();
    }

    public Set<String> getStringSet(String key) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                0);
        return pref.getStringSet(key, null);
    }

    public void removeAValue(String key) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                0);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.apply();
    }

    public void removeAll() {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    public void saveFCMToken(String refreshedToken) {
        putStringValue(FCM, refreshedToken);
    }

    public String getFCMToken() {
        return getStringValue(FCM, null);
    }

    public void saveAccountLogin(String email) {
        putStringValue(ACCOUNT_LOGIN, email);
    }

    public String getAccountLogin() {
        return getStringValue(ACCOUNT_LOGIN, null);
    }

    public void setVerifyStatus(int verifyStatus) {
        putIntValue(VERIFY, verifyStatus);
    }

    public int getVerifyStatus() {
        return getIntValue(VERIFY, -1);
    }


    public void setIsFacebookLogin(boolean isFacebookLogin) {
        putBooleanValue(IS_FACEBOOK_LOGIN, isFacebookLogin);
    }

    public boolean isFacebookLogin() {
        return getBooleanValue(IS_FACEBOOK_LOGIN);
    }


    final String LOCALE = "LOCALE";
    public static String ENG = "en";
    public static String LT = "lt";

    public void setLocale(String locale) {
        putStringValue(LOCALE, locale);
    }

    public String getLocale() {
        return getStringValue(LOCALE, ENG);
    }

    void setLocale(Context context, String languageToLoad) {

        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }
}

