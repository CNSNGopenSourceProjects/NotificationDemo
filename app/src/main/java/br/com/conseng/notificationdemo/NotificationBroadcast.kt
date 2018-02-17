package br.com.conseng.notificationdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.widget.Toast

/**
 * Exigiu que fosse declarado no manifesto o <receiver> e as ações do filtro.
 */
class NotificationBroadcast : BroadcastReceiver() {
    /**
     * Receive the broadcast in background.
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     * @see [https://www.youtube.com/watch?v=wMS-m29zH20]
     */
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            NOTIFY_PREVIOUS -> {
                Toast.makeText(context, "Handle the PREVIOUS button", Toast.LENGTH_LONG).show()
            }
            NOTIFY_DELETE -> {
                Toast.makeText(context, "Handle the DELETE button", Toast.LENGTH_LONG).show()
            }
            NOTIFY_PAUSE -> {
                Toast.makeText(context, "Handle the PAUSE button", Toast.LENGTH_LONG).show()
                // TODO: btn_play.visibility=visible e btn_pause.visibility=gone
                            }
            NOTIFY_PLAY -> {
                Toast.makeText(context, "Handle the PLAY button", Toast.LENGTH_LONG).show()
                // TODO: btn_play.visibility=gone e btn_pause.visibility=visible
            }
            NOTIFY_NEXT -> {
                Toast.makeText(context, "Handle the NEXT button", Toast.LENGTH_LONG).show()
            }
        }
    }
}