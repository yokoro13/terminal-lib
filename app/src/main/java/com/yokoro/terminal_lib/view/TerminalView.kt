package com.yokoro.terminal_lib.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.text.InputType
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.BaseInputConnection
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import com.yokoro.terminal_lib.entity.TerminalBuffer
import kotlin.math.abs


class TerminalView : View {

    private var inputListener: InputListener? = null
    private var gestureListener: GestureListener? = null

    lateinit var termBuffer: TerminalBuffer

    private var textSize: Int = 25

    var layoutBottom: Int = 0

    private var terminalRenderer: TerminalRenderer = TerminalRenderer(textSize)

    set(width) {
        field = width / terminalRenderer.fontWidth
    }
    var screenRowSize: Int = 0
    set(height) {
        field = (height-100) / terminalRenderer.fontHeight - 1
    }

    private var oldY = 0

    var keyboardHeight: Int = 0
    var isShowingKeyboard: Boolean =  false

    private var isDisplaying = false        // 画面更新中はtrue

    constructor(context: Context?): super(context) {
        focusable()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        focusable()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle){
        focusable()
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        outAttrs.inputType = InputType.TYPE_NULL
        outAttrs.imeOptions = EditorInfo.IME_FLAG_NO_FULLSCREEN
        return BaseInputConnection(this, false)
    }

    override fun onCheckIsTextEditor(): Boolean {
        return true
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if(gestureListener != null) {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    oldY = event.rawY.toInt()
                    gestureListener?.onDown()
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    gestureListener?.onMove()
                    if (oldY > event.rawY) {
                        scrollDown()
                    }
                    if (oldY < event.rawY) {
                        scrollUp()
                    }
                }
                else -> {
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val dispatchFirst = super.dispatchKeyEvent(event)

        if (event.action == KeyEvent.ACTION_UP){
            // 入力を通知

            if(inputListener != null) {
                inputListener?.onKey(event.unicodeChar.toChar())
            }
        }
        return dispatchFirst
    }

    override fun onDraw(canvas: Canvas) {
        if (!isDisplaying) {
            isDisplaying = true
            val keyboard = if (isShowingKeyboard) keyboardHeight else 0
            terminalRenderer.render(termBuffer, canvas, termBuffer.topRow, cursor, cursorIsInScreen(), paddingBottom, keyboard)
            isDisplaying = false
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        resolveSize(widthMeasureSpec, heightMeasureSpec)

        layoutBottom = MeasureSpec.getSize(heightMeasureSpec)

        viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            getWindowVisibleDisplayFrame(rect)
            isShowingKeyboard = layoutBottom > rect.bottom
            keyboardHeight = if (isShowingKeyboard) abs((layoutBottom - rect.bottom)) else 0
            layoutBottom = rect.bottom
        }
    }

    fun setInputListener(listener: InputListener){
        this.inputListener = listener
    }

    fun setGestureListener(listener: GestureListener){
        this.gestureListener = listener
    }

    private fun scrollDown() {
        if (termBuffer.totalLines > termBuffer.screenRowSize) {
            // 一番下の行までしか表示させない
            if (termBuffer.topRow + termBuffer.screenRowSize < termBuffer.totalLines) {
                //表示する一番上の行を１つ下に
                termBuffer.topRow++
                if (cursorIsInScreen()) {
                    setEditable(true)
                    cursor.y = termBuffer.currentRow - termBuffer.topRow
                } else {
                    setEditable(false)
                }
                invalidate()
            }
        }
    }

    private fun scrollUp() {
        if (termBuffer.totalLines > termBuffer.screenRowSize) {
            //表示する一番上の行を１つ上に
            termBuffer.topRow--
            // カーソルが画面内にある
            if (cursorIsInScreen()) {
                setEditable(true)
                cursor.y = termBuffer.currentRow - termBuffer.topRow
            } else { //画面外
                setEditable(false)
            }
            invalidate()
        }
    }

    private fun focusable() {
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
    }

    // 画面の編集許可
    private fun setEditable(editable: Boolean) {
        if (editable) {
            focusable()
        } else {
            isFocusable = false
        }
    }

    fun setTitleBarSize(metrics: Float){
        terminalRenderer.titleBar = 20 * metrics.toInt() + paddingBottom
    }

}