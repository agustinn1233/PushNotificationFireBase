package com.picaflor.pushnotificationfirebase

import android.R
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val TAG = "MyAppLOG"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val CHANNEL_ID = "Bestmarts"
    private val CHANNEL_NAME = "Bestmarts"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            sendNotificationOreo(remoteMessage);
        }else{
            sendNotification(remoteMessage);
        }

        super.onMessageReceived(remoteMessage)
    }

    @SuppressLint("LongLogTag")
    private fun sendNotification(remoteMessage: RemoteMessage) {
        if (!isAppIsInBackground(applicationContext)) {
            //foreground app
            Log.e("remoteMessage foreground", remoteMessage.data.toString())
            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body
            val resultIntent = Intent(applicationContext, MainActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationBuilder = NotificationCompat.Builder(
                applicationContext, CHANNEL_ID
            )
            notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification_overlay)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setNumber(10)
                .setTicker("Bestmarts")
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info")
            notificationManager.notify(1, notificationBuilder.build())
        } else {
            Log.e("remoteMessage background", remoteMessage.data.toString())
            val data: Map<*, *> = remoteMessage.data
            val title: String? = data.get("title") as String?
            val body: String? = data.get("body") as String?
            val resultIntent = Intent(applicationContext, MainActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationBuilder = NotificationCompat.Builder(
                applicationContext, CHANNEL_ID
            )
            notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification_overlay)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setNumber(10)
                .setTicker("Bestmarts")
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info")
            notificationManager.notify(1, notificationBuilder.build())
        }
    }

    fun isAppIsInBackground(context: Context): Boolean {
        try {
            val activityManager = context
                .applicationContext.getSystemService(
                    ACTIVITY_SERVICE
                ) as ActivityManager
            val packageName = context.packageName
            val appProcesses = activityManager
                .runningAppProcesses ?: return false
            for (appProcess in appProcesses) {
                // The name of the process that this object is associated with.
                if (appProcess.processName == packageName && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "isAppOnForeground exception!", e)
        }
        return false
    }


    @SuppressLint("NewApi")
    private fun sendNotificationOreo(remoteMessage: RemoteMessage) {
        if (!isAppIsInBackground(applicationContext)) {
            //foreground app
            Log.e("remoteMessage", remoteMessage.data.toString())
            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body
            val resultIntent = Intent(applicationContext, MainActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val defaultsound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val oreoNotification = OreoNotification(this)
            val builder = oreoNotification.getOreoNotification(
                title,
                body,
                pendingIntent,
                defaultsound,
                java.lang.String.valueOf(R.drawable.ic_notification_overlay)
            )
            val i = 0
            oreoNotification.manager!!.notify(i, builder.build())
        } else {
            Log.e("remoteMessage", remoteMessage.data.toString())
            val title = remoteMessage.data["title"]
            val body = remoteMessage.data["body"]
            val resultIntent = Intent(applicationContext, MainActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val defaultsound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val oreoNotification = OreoNotification(this)
            val builder = oreoNotification.getOreoNotification(
                title,
                body,
                pendingIntent,
                defaultsound,
                java.lang.String.valueOf(R.drawable.ic_notification_overlay)
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val i = 0
            oreoNotification.manager!!.notify(i, builder.build())
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        super.onNewToken(token)
    }

}