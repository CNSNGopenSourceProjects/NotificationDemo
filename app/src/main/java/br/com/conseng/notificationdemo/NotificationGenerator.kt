package br.com.conseng.notificationdemo

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat

/**
 * Class to generate the notification to open the Activity.
 */
class NotificationGenerator {

    private val NOTIFICATION_ID_OPEN_ACTIVITY = 9

    fun openActivityNotification(context: Context) {
        val nc = NotificationCompat.Builder(context)
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notifyIntent = Intent(context, MainActivity::class.java)
        notifyIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        nc.setContentIntent(pendingIntent)

        nc.setSmallIcon(R.mipmap.ic_launcher)
        nc.setAutoCancel(true)
        nc.setContentTitle("Notification Demo title")
        nc.setContentText("Please, click here to start the activity")

        nm.notify(NOTIFICATION_ID_OPEN_ACTIVITY, nc.build())
    }
}