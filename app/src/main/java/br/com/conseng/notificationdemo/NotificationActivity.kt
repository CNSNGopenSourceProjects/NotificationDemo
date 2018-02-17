package br.com.conseng.notificationdemo

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.Toast
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

/**
 * Para poder executar este exemplo foi necessário incluir a dependência: 'com.android.support:support-v4:26.1.0'
 * @see [https://www.youtube.com/watch?v=VouATjZdIWo]
 * @see [https://developer.android.com/training/notify-user/expanded.html]
 */
class NotificationActivity : AppCompatActivity() {

    private val TAG = "Activity NOTIFICATION"

    private var ng: NotificationGenerator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        printCurrentState("onCreate")
    }

    /**
     * Create the notification that open the ativity.
     */
    fun onClickOpenActivity(view: View) {
        if (null == ng) ng = NotificationGenerator()
        ng!!.openActivityNotification(applicationContext)
        Toast.makeText(this, getString(R.string.msg_notification_sent), Toast.LENGTH_SHORT).show()
    }

    /**
     * Go back to the previous activity, using the parent declared on manifest.
     */
    fun onClickGoBack (view: View) {
        this.finish()
    }
    /**
     * Show a notification with big content (music control).
     */
    fun onClickBigContent(view: View) {
        if (null == ng) ng = NotificationGenerator()
        ng!!.customBigNotification(applicationContext)
        Toast.makeText(this, getString(R.string.msg_big_notification), Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        printCurrentState("onStart")
    }

    override fun onResume() {
        super.onResume()
        printCurrentState("onResume")
    }

    override fun onPause() {
        super.onPause()
        printCurrentState("onPause")
    }

    override fun onStop() {
        super.onStop()
        printCurrentState("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        printCurrentState("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        printCurrentState("onDestroy")
    }

    private fun printCurrentState(estado:String) {
        Log.d(TAG, "state=$estado")
//        println("$TAG : state=$estado")
    }
}
