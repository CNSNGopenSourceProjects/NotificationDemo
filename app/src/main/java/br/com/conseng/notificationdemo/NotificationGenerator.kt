package br.com.conseng.notificationdemo

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES.O
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews

const val NOTIFY_PREVIOUS = "br.com.conseng.notificationdemo.previous"
const val NOTIFY_DELETE = "br.com.conseng.notificationdemo.delete"
const val NOTIFY_PAUSE = "br.com.conseng.notificationdemo.pause"
const val NOTIFY_PLAY = "br.com.conseng.notificationdemo.play"
const val NOTIFY_NEXT = "br.com.conseng.notificationdemo.next"

const val NOTIFICATION_ID_OPEN_ACTIVITY = 9
const val NOTIFICATION_ID_BIG_CONTENT = 99

/**
 * Class to generate the notification to open the Activity.
 * @see [http://www.vogella.com/tutorials/AndroidNotifications/article.html]
 */
class NotificationGenerator {

    /**
     * Create a notification that calls the activity when clicked.
     */
    fun openActivityNotification(context: Context) {
        // prepare intent which is triggered if the notification is selected
        val resultIntent = Intent(context, MainActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        // build notification
        val nBuilder = NotificationCompat.Builder(context)
                .setContentTitle("Notification Demo title")
                .setContentText("Please, click here to start the activity")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID_OPEN_ACTIVITY, nBuilder.build())
    }

    /**
     * Create a notification with big content (music control).
     */
    fun customBigNotification(context: Context) {
        val expandedView = RemoteViews(context.packageName, R.layout.big_notification)
        expandedView.setTextViewText(R.id.lo_text_song_name, "Adele")

        val notifyIntent = Intent(context, MainActivity::class.java)
        notifyIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val nBuilder = NotificationCompat.Builder(context)
                .setContentTitle("Music Player")
                .setContentText("Control Audio")
                .setSmallIcon(R.drawable.ic_action_play)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setCustomBigContentView(expandedView)

        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID_BIG_CONTENT, nBuilder.build())

        setListeners(expandedView, context)
    }

    /**
     * Handle the control buttons.
     */
    private fun setListeners(view: RemoteViews, context: Context) {
        val intentPrevious = Intent(NOTIFY_PREVIOUS)
        val pendingIntentPrevious = PendingIntent.getBroadcast(context, O, intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btn_previous, pendingIntentPrevious)

        val intentDelete = Intent(NOTIFY_DELETE)
        val pendingIntentDelete = PendingIntent.getBroadcast(context, O, intentDelete, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btn_previous, pendingIntentDelete)

        val intentPause = Intent(NOTIFY_PAUSE)
        val pendingIntentPause = PendingIntent.getBroadcast(context, O, intentPause, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btn_previous, pendingIntentPause)

        val intentNext = Intent(NOTIFY_NEXT)
        val pendingIntentNext = PendingIntent.getBroadcast(context, O, intentNext, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btn_previous, pendingIntentNext)

        val intentPlay = Intent(NOTIFY_PLAY)
        val pendingIntentPlay = PendingIntent.getBroadcast(context, O, intentPlay, PendingIntent.FLAG_UPDATE_CURRENT)
        view.setOnClickPendingIntent(R.id.btn_previous, pendingIntentPlay)
    }
}