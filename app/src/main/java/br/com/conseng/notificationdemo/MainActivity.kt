package br.com.conseng.notificationdemo

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Cria uma Activity inicial para verificar como se comporta uma notificação que retorna a
 * uma activity outra que não a principal.  Para a correta navegação, o MANIFESTO foi
 * atualizado para identificar o parentesco corretamente entre as Activities.
 */
class MainActivity : AppCompatActivity() {

    private val TAG = "Activity MAIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        printCurrentState("onCreate")
    }

    /**
     * Open the notification demo screen.
     */
    fun onClickGoToActivity2(v: View) {
        val intent= Intent(this, NotificationActivity::class.java)
        startActivity(intent)
    }

    /**
     * Terminate this app.
     */
    fun onClickTerminate(v: View) {
        this.finish()
        System.exit(0)
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
