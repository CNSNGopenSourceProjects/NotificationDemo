package br.com.conseng.notificationdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast

/**
 * Para poder executar este exemplo foi necessário incluir a dependência: 'com.android.support:support-v4:26.1.0'
 * @see [https://www.youtube.com/watch?v=VouATjZdIWo]
 * @see [https://developer.android.com/training/notify-user/expanded.html]
 */
class NotificationActivity : AppCompatActivity() {

    private val TAG = "Activity NOTIFICATION"

    private var ng: NotificationGenerator? = null

    private var serviceIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        printCurrentState("onCreate")
    }

    /**
     * Handle all notification test buttons.
     * @param [view] identify the button.
     */
    fun showNotification(view: View) {
        if (null == ng) ng = NotificationGenerator()

        when (view.id) {
            R.id.btn_regular_notification -> {
                ng!!.showRegularNotification(applicationContext)
                Toast.makeText(this, getString(R.string.msg_regular_notification_sent), Toast.LENGTH_SHORT).show()
            }
            R.id.btn_music_notification -> {
                serviceIntent = Intent(applicationContext, NotificationService::class.java)
                serviceIntent!!.setAction(STARTFOREGROUND_ACTION)
                startService(serviceIntent)
                ng!!.showBigContentMusicPlayer(applicationContext)
                Toast.makeText(this, getString(R.string.msg_big_notification_music_sent), Toast.LENGTH_SHORT).show()
            }
            R.id.btn_big_text_notification -> {
                ng!!.showBigTextNotification(applicationContext)
                Toast.makeText(this, getString(R.string.msg_big_notification_text_sent), Toast.LENGTH_SHORT).show()
            }
            R.id.btn_big_picture_notification -> {
                ng!!.showBigPictureNotification(applicationContext)
                Toast.makeText(this, getString(R.string.msg_big_notification_picture_sent), Toast.LENGTH_SHORT).show()
            }
            R.id.btn_big_inbox_notification -> {
                ng!!.showBigInboxNotification(applicationContext)
                Toast.makeText(this, getString(R.string.msg_big_notification_inbox_sent), Toast.LENGTH_SHORT).show()
            }
            R.id.btn_go_back -> {
                this.finish()
            }
        }
    }

    override fun onStart() {
        printCurrentState("onStart")
        super.onStart()
    }

    override fun onResume() {
        printCurrentState("onResume")
        super.onResume()
    }

    override fun onPause() {
        printCurrentState("onPause")
        super.onPause()
    }

    override fun onStop() {
        printCurrentState("onStop")
        super.onStop()
    }

    override fun onRestart() {
        printCurrentState("onRestart")
        super.onRestart()
    }

    override fun onDestroy() {
        printCurrentState("onDestroy")
        super.onDestroy()
    }

    private fun printCurrentState(estado: String) {
        Log.d(TAG, "state=$estado")
//        println("$TAG : state=$estado")
    }
}
