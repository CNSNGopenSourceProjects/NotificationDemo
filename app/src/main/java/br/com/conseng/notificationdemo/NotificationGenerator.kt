package br.com.conseng.notificationdemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION_CODES.O
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
 * @property [notificationIntentClass] The component class that is to be used for the notification intent.
 *           The default class, for this example, is [NotificationActivity].
 * @see [http://www.vogella.com/tutorials/AndroidNotifications/article.html]
 * @see [https://www.youtube.com/watch?v=VouATjZdIWo]
 * @see [https://www.youtube.com/watch?v=3FJNOrfBQEA]
 * @see [https://www.youtube.com/watch?v=wMS-m29zH20]
 */
class NotificationGenerator(var notificationIntentClass: Class<*> = NotificationActivity::class.java) {

    private var notificationManager: NotificationManager? = null
    private var notificationChannel: NotificationChannel? = null
    private val channelId = "br.com.conseng.notificationdemo"
    private val description = "Test notification"

    /**
     * Create a notification that calls the activity when clicked.
     * Para poder recompor a estruturas das activities, é necessário declarar o parentesco no manifesto
     * e incluir o atributo launchMode="singleInstance" para MainActivity a fim de que esta exista ao sair
     * da NotificationActivity.
     * @param [context] application context for associate the notification with.
     */
    fun openActivityNotification(context: Context) {
        val nBuilder = getNotificationBuilder(context,
                "Notification Demo title",
                "Please, click here to start the activity",
                R.drawable.ic_stat_notification)

        notificationManager?.notify(NOTIFICATION_ID_OPEN_ACTIVITY, nBuilder.build())
    }

    /**
     * Create a notification with big content (music control).
     * @param [context] application context for associate the notification with.
     */
    fun customBigNotification(context: Context) {
        val expandedView = RemoteViews(context.packageName, R.layout.big_notification)
        expandedView.setTextViewText(R.id.lo_text_song_name, "Adele")
        setListeners(expandedView, context)

        val nBuilder = getNotificationBuilder(context,
                "Music Player",
                "Control Audio",
                R.drawable.ic_stat_big_content)
        lateinit var notification: Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            nBuilder.setCustomBigContentView(expandedView)
            notification = nBuilder.build()
        } else {
            notification = nBuilder.build()
            notification.bigContentView = expandedView
        }

        notificationManager?.notify(NOTIFICATION_ID_BIG_CONTENT, notification)
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

    /**
     * Initialize the notification manager and channel Id.
     * The notification builder has the basic initialization:
     *     - AutoCancel=true
     *     - LargeIcon = SmallIcon
     * @param [context] application context for associate the notification with.
     * @param [notificationTitle] notification title.
     * @param [notificationText] notification text.
     * @param [notificationIconId] notification icon id from application resource.
     * @return the PendingIntent to be used on this notification.
     */
    private fun getNotificationBuilder(context: Context,
                                       notificationTitle: String, notificationText: String, notificationIconId: Int): Notification.Builder {
        if (null == notificationManager) {
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        val intent = getIntent(context)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        lateinit var builder: Notification.Builder
        if (Build.VERSION.SDK_INT >= O) {
            if (null == notificationChannel) {
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel?.enableLights(true)
                notificationChannel?.lightColor = Color.GREEN
                notificationChannel?.enableVibration(false)
                notificationManager?.createNotificationChannel(notificationChannel)
            }
            builder = Notification.Builder(context, channelId)
        } else {
            builder = Notification.Builder(context)
        }

        builder.setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setSmallIcon(notificationIconId)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, notificationIconId))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        return builder
    }

    /**
     * Retorna a Intent que será utilizada nesta notificação.
     * @param [context] application context for associate the notification with.
     * @return the activity associated to the notification.
     */
    private fun getIntent(context: Context): Intent {
        val intent = Intent(context, notificationIntentClass)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        return intent
    }
}