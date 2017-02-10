package com.techmonkey.topyaps.firebaseService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.techmonkey.topyaps.DetailActivity;
import com.techmonkey.topyaps.MainActivity;
import com.techmonkey.topyaps.R;
import com.techmonkey.topyaps.helper.Constants;
import com.techmonkey.topyaps.models.PostJSONData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by P KUMAR on 09-01-2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "DATA";
    private final Gson gson = new Gson();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent intent=new Intent(this, DetailActivity.class);
        /*intent.putExtra("NotificationMessage", remoteMessage);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
        /*Map<String, String> data = remoteMessage.getData();
        for (Map.Entry<String, String> dataEntry : data.entrySet()) {
            intent.putExtra(Constants.REFERENCE.TOPYAPS_DATA, dataEntry.getValue());
        }*/
        intent.putExtra("key", remoteMessage.getNotification().getBody());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("TopYaps");
        builder.setContentText(remoteMessage.getNotification().getBody());
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }
}
