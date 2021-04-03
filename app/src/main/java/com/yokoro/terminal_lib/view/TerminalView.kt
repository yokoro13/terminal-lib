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
import android.view.inputmethod.InputMethodManager
import com.yokoro.terminal_lib.repository.TerminalRepository
import com.yokoro.terminal_lib.usecase.cursor.*
import com.yokoro.terminal_lib.usecase.escapesequence.EscapeSequenceUseCase
import com.yokoro.terminal_lib.usecase.screen.ScreenUseCase
import com.yokoro.terminal_lib.usecase.terminal.*
import com.yokoro.terminal_lib.usecase.terminalbuffer.*
import com.yokoro.terminal_lib.viewmodel.TerminalViewModel
import kotlinx.coroutines.runBlocking
import kotlin.math.abs


class TerminalView : View {
    private var textSize: Int = 25

    // TODO 依存性の注入をどうにかする
    private val terminalRepository: TerminalRepository = TerminalRepository()
    private val terminalBufferUseCase = TerminalBufferUseCase(
        AddNewRow(terminalRepository),
        GetTerminalBuffer(terminalRepository),
        SetText(terminalRepository),
        SetColor(terminalRepository)
    )

    private val cursorUseCase = CursorUseCase(
        SetCursor(terminalRepository),
        GetCursor(terminalRepository),
        SetDisplayingState(terminalRepository),
        IsDisplaying(terminalRepository)
    )

    private val terminalUseCase = TerminalUseCase(
        CreateTerminal(terminalRepository),
        Resize(terminalRepository),
        GetScreenSize(terminalRepository),
        SetTopRow(terminalRepository),
        GetTopRow(terminalRepository),
        terminalBufferUseCase
    )

    private val screenUseCase = ScreenUseCase(
        terminalBufferUseCase,
        terminalUseCase,
        cursorUseCase
    )

    private val escapeSequenceUseCase = EscapeSequenceUseCase(
        GetScreenSize(terminalRepository),
        GetTopRow(terminalRepository),
        SetTopRow(terminalRepository),
        cursorUseCase,
        terminalBufferUseCase
    )

    private val viewModel: TerminalViewModel = TerminalViewModel(
        escapeSequenceUseCase,
        terminalUseCase,
        terminalBufferUseCase,
        screenUseCase,
        cursorUseCase
    )

    private var terminalRenderer: TerminalRenderer = TerminalRenderer(textSize)

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
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                viewModel.touchedY = event.rawY.toInt()
                showKeyboard()
                // TODO resize
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                hideKeyboard()
                // TODO resize
                if (viewModel.touchedY > event.rawY) {
                    runBlocking {
                        viewModel.scrollDown()
                    }
                }
                if (viewModel.touchedY < event.rawY) {
                    runBlocking {
                        viewModel.scrollUp()
                    }
                }
                invalidate()
            }
            else -> {
            }
        }

        return super.dispatchTouchEvent(event)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val dispatchFirst = super.dispatchKeyEvent(event)
        if (event.action == KeyEvent.ACTION_UP){
            viewModel.writeToBufferAtCursor(event.unicodeChar.toChar())
        }
        return dispatchFirst
    }

    override fun onDraw(canvas: Canvas) {
        if (!viewModel.isUpdatingScreen) {
            runBlocking {

                viewModel.isUpdatingScreen = true
                val keyboard =
                    if (viewModel.isShowingKeyboard) terminalRenderer.keyboardHeight else 0

                terminalRenderer.render(
                    viewModel.getTerminalBuffer(),
                    viewModel.getTopRow(),
                    viewModel.getScreenSize(),
                    viewModel.getCursor(),
                    canvas,
                    paddingBottom,
                    keyboard)
                viewModel.isUpdatingScreen = false
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        resolveSize(widthMeasureSpec, heightMeasureSpec)

        terminalRenderer.layoutBottom = MeasureSpec.getSize(heightMeasureSpec)

        viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            getWindowVisibleDisplayFrame(rect)
            viewModel.isShowingKeyboard = terminalRenderer.layoutBottom > rect.bottom
            terminalRenderer.keyboardHeight = if (viewModel.isShowingKeyboard) abs((terminalRenderer.layoutBottom - rect.bottom)) else 0
            terminalRenderer.layoutBottom = rect.bottom
        }
    }

    fun addText(text: String) {
        text.toCharArray().forEach { viewModel.writeToBufferAtCursor(it) }
    }

    fun toggleCtlBtn() {

    }

    fun setTitleBarSize(metrics: Float){
        terminalRenderer.titleBarHeight = 20 * metrics.toInt() + paddingBottom
    }

    private fun focusable() {
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
            viewModel.writeToBufferAtCursor('\u0008')
            invalidate()
            return true
        }
        return false
    }

    // キーボードを表示させる
    private fun showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    //　キーボードを隠す
    private fun hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}