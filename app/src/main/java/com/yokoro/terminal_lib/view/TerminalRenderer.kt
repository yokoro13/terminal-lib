package com.yokoro.terminal_lib.view

import android.graphics.*
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.entity.TerminalRow
import kotlin.math.abs
import kotlin.math.ceil

/**
 * Android を使う場合に必要な情報や描画操作を記述
 *
 */

class TerminalRenderer(textSize: Int) {
    private val textPaint: Paint = Paint()

    val fontWidth: Int    // 文字幅
    private val fontLineSpacing: Int    // 行のスペース
    var titleBarHeight: Int = 0
    var fontHeight: Int = 0
    var layoutBottom: Int = 0
    var keyboardHeight: Int = 0

    var screenHeight: Int = 0
        set(height) {
            field = (height-100) / fontHeight - 1
        }

    fun render(buffer: ArrayList<TerminalRow>, topRow: Int, screenSize: ScreenSize,
               cursor: Cursor, canvas: Canvas, pad: Int, keyboard: Int) {
        val displayRows = if (topRow + screenSize.columns <= buffer.size){
            topRow + screenSize.columns
        } else {
            buffer.size
        }

        var padding = pad

        if(keyboard != 0) {
            val inv = screenSize.columns - (keyboard+pad) / fontLineSpacing

            val keyPadding = if (cursor.y <= inv) {
                0
            } else {
                (cursor.y - 1 - inv) * fontLineSpacing
            }
            padding += keyPadding
        }

        for (row in topRow until displayRows) {
            canvas.drawText(
                buffer[row].text,
                0,
                screenSize.columns,
                0f,
                titleBarHeight + (fontLineSpacing * (row-topRow).toFloat())-padding,
                textPaint
            )
        }
        moveToSavedCursor(canvas, cursor, padding)
    }

    private fun moveToSavedCursor(canvas: Canvas, cursor: Cursor, pad: Int) {
        if(cursor.isDisplaying) {
            canvas.drawRect(
                (cursor.x) * fontWidth.toFloat(),
                titleBarHeight + (fontLineSpacing * (cursor.y - 1))-pad.toFloat(),
                (cursor.x + 1) * fontWidth.toFloat(),
                titleBarHeight + (fontLineSpacing * cursor.y).toFloat()-pad,
                textPaint)
        }
    }

    init {
        textPaint.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)// 等幅フォント
        textPaint.isAntiAlias = true
        textPaint.textSize = textSize.toFloat()

        fontLineSpacing = ceil(textPaint.fontSpacing).toInt()
        fontHeight = (abs(textPaint.fontMetrics.top) + abs(textPaint.fontMetrics.bottom)).toInt()
        fontWidth = textPaint.measureText(" ").toInt()
    }
}