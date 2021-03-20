package com.yokoro.terminal_lib

import android.annotation.SuppressLint
import android.content.*
import android.graphics.Point
import android.os.*
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.view.TerminalView

class MainActivity : AppCompatActivity() {

    private lateinit var termView: TerminalView//ディスプレイ

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())

        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getSize(Point())

        termView = findViewById(R.id.main_display)
        termView.setTitleBarSize(resources.displayMetrics.density)

        termView.invalidate()

        findViewById<View>(R.id.btn_up).setOnClickListener { termView.addText("\u001b" + "[A") }

        findViewById<View>(R.id.btn_down).setOnClickListener { termView.addText("\u001b" + "[B") }

        findViewById<View>(R.id.btn_right).setOnClickListener { termView.addText("\u001b" + "[C") }
        findViewById<View>(R.id.btn_left).setOnClickListener { termView.addText("\u001b" + "[D") }

        findViewById<View>(R.id.btn_esc).setOnClickListener { termView.addText("\u001b") }
        findViewById<View>(R.id.btn_tab).setOnClickListener { termView.addText("\u0009") }
        findViewById<View>(R.id.btn_ctl).setOnClickListener { termView.toggleCtlBtn() }
    }

    private fun handleFailure(failure: Failure){
        when (failure) {
            is Failure.UninitializedException -> { throw IllegalArgumentException("") }
        }
    }
}
