package com.parduota.parduota;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.parduota.parduota.utils.SharePrefManager;

/**
 * Created by huy_quynh on 6/27/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FIREBASE";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        String token = SharePrefManager.getInstance(this).getAccessToken();

        SharePrefManager.getInstance(this).saveFCMToken(refreshedToken);


    }
}
