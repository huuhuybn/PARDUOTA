package com.parduota.parduota;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.User;
import com.parduota.parduota.utils.SharePrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by huy_quynh on 6/27/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService implements Constant {
    private static final String TAG = "MESSAGE_FIRE";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "From: " + remoteMessage.getMessageType() + " " + remoteMessage.getData().toString());


        SharePrefManager.getInstance(this).addCountNoti();

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            String type_ = remoteMessage.getData().toString();
            try {
                JSONObject jsonObject = new JSONObject(type_);
                int type = jsonObject.getInt("TYPE");

                Log.e("TYPE", type + "");
                switch (type) {

                    // coming message
                    case 19:
                        Intent intent = new Intent(COMMING_MESSAGE);
                        sendBroadcast(intent);
                        break;

                    // update terms and condition
                    case 14:
                        User user = SharePrefManager.getInstance(this).getUser();
                        user.setTerm_accept(0);
                        SharePrefManager.getInstance(this).saveUser(user);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            showNotification(remoteMessage.getFrom(), remoteMessage.getNotification().getBody());
            Intent intent = new Intent(COMMING_MESSAGE);
            intent.putExtra(DATA, remoteMessage);
            sendBroadcast(intent);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }

    private void showNotification(String from, String message) {

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, MainAC.class);
        // Send data to NotificationView Class
        intent.putExtra("title", from);
        intent.putExtra("text", message);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(R.mipmap.ic_launcher)
                // Set Ticker Message
                .setTicker(getString(R.string.app_name))
                // Set Title
                .setContentTitle(getString(R.string.app_name))
                // Set Text
                .setContentText(message)
                // Add an Action Button below Notification
                .addAction(R.mipmap.ic_launcher, "Action Button", pIntent)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Dismiss Notification
                .setAutoCancel(true);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
       (notificationmanager).notify(0, builder.build());
    }


}
