package br.com.conseng.notificationdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Para poder executar este exemplo foi necessário incluir a dependência: 'com.android.support:support-v4:26.1.0'
 * @see [https://www.youtube.com/watch?v=VouATjZdIWo]
 */
class MainActivity : AppCompatActivity() {

    private lateinit var btnOpenActivity: Button
    private lateinit var btnBigContent: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOpenActivity = btn_notification_activity
        btnBigContent = btn_big_content
    }

    /**
     * Create the notification that open the ativity.
     */
    fun onClickOpenActivity (view: View) {
        NotificationGenerator().openActivityNotification(applicationContext)
    }

    fun onClickBigContent (view: View) {
        // TODO:
    }
}
