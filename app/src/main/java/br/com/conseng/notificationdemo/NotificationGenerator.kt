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
import android.view.View
import android.widget.RemoteViews

const val NOTIFY_PREVIOUS = "br.com.conseng.notificationdemo.previous"
const val NOTIFY_DELETE = "br.com.conseng.notificationdemo.delete"
const val NOTIFY_PAUSE = "br.com.conseng.notificationdemo.pause"
const val NOTIFY_PLAY = "br.com.conseng.notificationdemo.play"
const val NOTIFY_NEXT = "br.com.conseng.notificationdemo.next"
const val STARTFOREGROUND_ACTION = "br.com.conseng.notificationdemo.startforeground";

const val NOTIFICATION_ID_REGULAR = 9
const val NOTIFICATION_ID_BIG_CONTENT = 99
const val NOTIFICATION_ID_BIG_TEXT_CONTENT = 666
const val NOTIFICATION_ID_BIG_PICTURE_CONTENT = 777
const val NOTIFICATION_ID_BIG_INBOX_CONTENT = 999

/**
 * Class to generate the notification to open the Activity.
 * @property [notificationIntentClass] The component class that is to be used for the notification intent.
 *           The default class, for this example, is [NotificationActivity].
 * @see [https://developer.android.com/guide/topics/ui/notifiers/notifications.html]
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
     * @param [context] application context for associate the notification with.
     */
    fun showRegularNotification(context: Context) {
        // Build the content of the notification
        val nBuilder = getNotificationBuilder(context,
                "Notification Demo title",
                "Please, click here to start the activity",
                R.drawable.ic_stat_notification,
                "Illustrate how an activity can be called clicking on the notification.")

        // Notification through notification manager
        notificationManager?.notify(NOTIFICATION_ID_REGULAR, nBuilder.build())
    }

    /**
     * Creates a Custom Notifications that is usually used by music player apps.
     * @param [context] application context for associate the notification with.
     * @see [http://www.tutorialsface.com/2015/08/android-custom-notification-tutorial/]
     */
    fun showBigContentMusicPlayer(context: Context) {
        // Using RemoteViews to bind custom layouts into Notification
        val smallView = RemoteViews(context.packageName, R.layout.status_bar)
        val bigView = RemoteViews(context.packageName, R.layout.status_bar_expanded)

        // showing default album image
        smallView.setViewVisibility(R.id.status_bar_icon, View.VISIBLE)
        smallView.setViewVisibility(R.id.status_bar_album_art, View.GONE)
        bigView.setImageViewBitmap(R.id.status_bar_album_art, BitmapFactory.decodeResource(context.resources, R.drawable.default_album_picture))
        setListeners(bigView, smallView, context)

        // Build the content of the notification
        val nBuilder = getNotificationBuilder(context,
                "Music Player",
                "Control Audio",
                R.drawable.ic_stat_big_content,
                "Illustrate how a big content notification can be created.")

        // Notification through notification manager
        lateinit var notification: Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            nBuilder.setCustomBigContentView(bigView)
            nBuilder.setCustomContentView(smallView)
            notification = nBuilder.build()
        } else {
            notification = nBuilder.build()
            notification.contentView = smallView
            notification.bigContentView = bigView
        }

        // Notification through notification manager
        notification.flags = Notification.FLAG_ONGOING_EVENT
        notificationManager?.notify(NOTIFICATION_ID_BIG_CONTENT, notification)
    }

    /**
     * Create a notification with big text content.
     * @param [context] application context for associate the notification with.
     */
    fun showBigTextNotification(context: Context) {
        val title = "Big Text Notification"
        val text = "Show a large text notification."
        val bigText = "Provide the longer text to be displayed in the big form of the " +
                "template in place of the content text. \n" +
                "The size of this text should be longer without compromise the notification presentation."

        // Assign a style of Big Text
        val style = Notification.BigTextStyle()
        style.setBigContentTitle(title)
        style.bigText(bigText)

        // Build the content of the notification
        val nBuilder = getNotificationBuilder(context, title, text, R.drawable.ic_stat_big_text,
                "Illustrate how a big text notification can be created.")
        nBuilder.setStyle(style)

        // Notification through notification manager
        val notification: Notification = nBuilder.build()

        notificationManager?.notify(NOTIFICATION_ID_BIG_TEXT_CONTENT, notification)
    }

    /**
     * Create a notification with big picture content.
     * @param [context] application context for associate the notification with.
     */
    fun showBigPictureNotification(context: Context) {
        val title = "Big Picture Notification"
        val text = "Show a big content picture style notification."
        val summary = "I like South Park and Cartman is my favority character."
        val largePicture = BitmapFactory.decodeResource(context.resources, R.drawable.eric_cartman)

        // Assign a style of Big Picture
        val style = Notification.BigPictureStyle()
        style.setBigContentTitle(title)
        style.setSummaryText(summary)
        style.bigPicture(largePicture)

        // Build the content of the notification
        val nBuilder = getNotificationBuilder(context, title, text, R.drawable.ic_stat_big_pigture,
                "Illustrate how a big picture notification can be created.")
        nBuilder.setStyle(style)

        // Notification through notification manager
        val notification: Notification = nBuilder.build()

        notificationManager?.notify(NOTIFICATION_ID_BIG_PICTURE_CONTENT, notification)
    }

    /**
     * Create a notification with big inbox content.
     * @param [context] application context for associate the notification with.
     */
    fun showBigInboxNotification(context: Context) {
        val title = "Inbox Regular Notification"
        val text = "Show a big content inbox style notification."

        // Assign a style of Big Inbox
        val style = Notification.InboxStyle()
        style.setBigContentTitle(title)
        style.addLine("Line one message")
        style.addLine("Line two message")
        style.addLine("Line three message")
        style.addLine("Line four message")
        style.addLine("Line five message")
        style.addLine("Line six message")
        style.addLine("Line seven message")

        // Build the content of the notification
        val nBuilder = getNotificationBuilder(context, title, text, R.drawable.ic_stat_big_inbox,
                "Illustrate how a big inbox notification can be created.")
        nBuilder.setStyle(style)

        // Notification through notification manager
        val notification: Notification = nBuilder.build()

        notificationManager?.notify(NOTIFICATION_ID_BIG_INBOX_CONTENT, notification)
    }

    /**
     * Handle the control buttons.
     * @param [bigView] remote view for big content.
     * @param [smallView] remote view for regular content.
     * @param [context] application context for associate the notification with.
     */
    private fun setListeners(bigView: RemoteViews, smallView: RemoteViews, context: Context) {
        val intentPrevious = Intent(context, NotificationService::class.java)
        intentPrevious.action = NOTIFY_PREVIOUS
        val pendingIntentPrevious = PendingIntent.getService(context, 0, intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT)
        bigView.setOnClickPendingIntent(R.id.status_bar_prev, pendingIntentPrevious)
        smallView.setOnClickPendingIntent(R.id.status_bar_prev, pendingIntentPrevious)

        val intentDelete = Intent(context, NotificationService::class.java)
        intentDelete.action = NOTIFY_DELETE
        val pendingIntentDelete = PendingIntent.getService(context, 0, intentDelete, PendingIntent.FLAG_UPDATE_CURRENT)
        bigView.setOnClickPendingIntent(R.id.status_bar_collapse, pendingIntentDelete)
        smallView.setOnClickPendingIntent(R.id.status_bar_collapse, pendingIntentDelete)

        val intentNext = Intent(context, NotificationService::class.java)
        intentNext.action = NOTIFY_NEXT
        val pendingIntentNext = PendingIntent.getService(context, 0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT)
        bigView.setOnClickPendingIntent(R.id.status_bar_next, pendingIntentNext)
        smallView.setOnClickPendingIntent(R.id.status_bar_next, pendingIntentNext)

        val intentPlay = Intent(context, NotificationService::class.java)
        intentPlay.action = NOTIFY_PLAY
        val pendingIntentPlay = PendingIntent.getService(context, 0, intentPlay, PendingIntent.FLAG_UPDATE_CURRENT)
        bigView.setOnClickPendingIntent(R.id.status_bar_play, pendingIntentPlay)
        smallView.setOnClickPendingIntent(R.id.status_bar_play, pendingIntentPlay)

        bigView.setTextViewText(R.id.status_bar_track_name, "Song Title")
        smallView.setTextViewText(R.id.status_bar_track_name, "Song Title")

        bigView.setTextViewText(R.id.status_bar_artist_name, "Artist Name")
        smallView.setTextViewText(R.id.status_bar_artist_name, "Artist Name")

        bigView.setTextViewText(R.id.status_bar_album_name, "Album Name")
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
     * @param [notificationTicker] notification ticker text for accessibility.
     * @return the PendingIntent to be used on this notification.
     */
    private fun getNotificationBuilder(context: Context,
                                       notificationTitle: String,
                                       notificationText: String,
                                       notificationIconId: Int,
                                       notificationTicker: String): Notification.Builder {
        // Define the notification channel for newest Android versions
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent = getPendingIntent(context)
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

        // Build the content of the notification
        builder.setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setSmallIcon(notificationIconId)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, notificationIconId))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker(notificationTicker)
        // Restricts the notification information when the screen is blocked.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setVisibility(Notification.VISIBILITY_PRIVATE)
        }

        return builder
    }

    /**
     * Retorna a Intent que será utilizada nesta notificação.
     * Para poder recompor a estruturas das activities, é necessário declarar o parentesco no manifesto
     * e incluir os atributos:
     *          + launchMode="singleTask"
     *          + taskAffinity=""
     *          + excludeFromRecents="true"
     * da NotificationActivity.
     * @param [context] application context for associate the notification with.
     * @return the activity associated to the notification.
     * @see [https://developer.android.com/guide/topics/ui/notifiers/notifications.html#NotificationResponse]
     */
    private fun getPendingIntent(context: Context): PendingIntent {
        val resultIntent = Intent(context, notificationIntentClass)
        resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val resultPendingIntent = PendingIntent.getActivity(context, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        return resultPendingIntent
    }
}