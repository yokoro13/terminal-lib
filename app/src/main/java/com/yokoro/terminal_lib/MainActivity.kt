package com.yokoro.terminal_lib

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.graphics.Point
import android.os.*
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.yokoro.terminal_lib.view.GestureListener
import com.yokoro.terminal_lib.view.InputListener
import com.yokoro.terminal_lib.view.TerminalView


/**
 * TODO 画面保持
 */
class MainActivity : AppCompatActivity(), InputListener, GestureListener{

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

        termView.setInputListener(this)
        termView.setGestureListener(this)
        termView.invalidate()

        findViewById<View>(R.id.btn_up).setOnClickListener { writeData("\u001b" + "[A") }

        findViewById<View>(R.id.btn_down).setOnClickListener { writeData("\u001b" + "[B") }

        findViewById<View>(R.id.btn_right).setOnClickListener { writeData("\u001b" + "[C") }
        findViewById<View>(R.id.btn_left).setOnClickListener { writeData("\u001b" + "[D") }

        findViewById<View>(R.id.btn_esc).setOnClickListener { writeData("\u001b") }
        findViewById<View>(R.id.btn_tab).setOnClickListener { writeData("\u0009") }
        findViewById<View>(R.id.btn_ctl).setOnClickListener { btnCtl = true }

        //SDK23以降はBLEをスキャンするのに位置情報が必要
        if (Build.VERSION.SDK_INT in 23..28) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 0)
        } else if(29 <= Build.VERSION.SDK_INT){
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }

        termView.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DEL) {
                if (state == State.CONNECTED) {
                    writeData("\u0008")
                } else {
                    termView.cursor.x--
                    termView.invalidate()
                }
                return@setOnKeyListener true
            }
            false
        }


    }

    // デバイスデータを保存する
    public override fun onStop() {
        super.onStop()
    }

    override fun onKey(text: Char) {

    }

    override fun onDown() {
        if(termView.isFocusable) {
            showKeyboard()
        }
    }

    override fun onMove() {
        //hideKeyboard()
    }

    // キーボードを表示させる
    private fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        termView.isFocusable = true
        termView.isFocusableInTouchMode = true
        termView.requestFocus()
        imm.showSoftInput(termView, InputMethodManager.SHOW_IMPLICIT)
    }

    //　キーボードを隠す
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(termView.windowToken, 0)
    }
}
