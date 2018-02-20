package br.com.conseng.notificationdemo

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

/**
 * In music player, playback of songs has to be done within a service which runs in background
 * even after the application is closed. We will create such service to handle the inputs given
 * through the buttons shown by the Notification layout
 */
class NotificationService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * Receive the big content actions in background.
     * @param [intent] The Intent supplied to startService, as given.
     *        This may be null if the service is being restarted after its process has gone away,
     *        and it had previously returned anything except START_STICKY_COMPATIBILITY.
     * @param [flags] Additional data about this start request.
     * @param [startId] A unique integer representing this specific request to start.     *
     * @return The return value indicates what semantics the system should use for the service's
     *         current started state.  It may be one of the constants associated with the
     *         START_CONTINUATION_MASK bits.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent!!.action
        Log.d("onStartCommand", "action=$action")
        when (action) {
            NOTIFY_PREVIOUS -> {
                Toast.makeText(applicationContext, "Handle the PREVIOUS button", Toast.LENGTH_LONG).show()
            }
            NOTIFY_PLAY -> {
                Toast.makeText(applicationContext, "Handle the PLAY button", Toast.LENGTH_LONG).show()
            }
            NOTIFY_NEXT -> {
                Toast.makeText(applicationContext, "Handle the NEXT button", Toast.LENGTH_LONG).show()
            }
            NOTIFY_DELETE -> {
                Toast.makeText(applicationContext, "Handle the DELETE button", Toast.LENGTH_LONG).show()
                stopForeground(true)
                stopSelf()
                // Terminate the notification
                val notificationManager=applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(NOTIFICATION_ID_BIG_CONTENT)
            }
        }
        return START_STICKY
    }
}