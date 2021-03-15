package com.yokoro.terminal_lib.view

import android.graphics.*
import com.yokoro.terminal_lib.entity.Terminal
import kotlin.math.abs
import kotlin.math.ceil

class TerminalRenderer(textSize: Int) {
    private val textPaint: Paint = Paint()

    val fontWidth: Int    // 文字幅
    private val fontLineSpacing: Int    // 行のスペース
    var titleBar: Int = 0
    var fontHeight: Int = 0

    fun render(term: Terminal, canvas: Canvas, topRow: Int, cursor: TerminalView.Cursor, showCursor: Boolean, pad: Int, keyboard: Int) {
        val displayRows = if (topRow + term.screenRowSize <= term.totalLines){
            topRow + term.screenRowSize
        } else {
            term.totalLines
        }

        var padding = pad

        if(keyboard != 0) {
            val inv = term.screenRowSize - (keyboard+pad) / fontLineSpacing

            val keyPadding = if (cursor.y <= inv) {
                0
            } else {
                (cursor.y - 1 - inv) * fontLineSpacing
            }
            padding += keyPadding
        }

        for (row in topRow until displayRows) {
            canvas.drawText(term.getRowText(row), 0, term.screenColumnSize, 0f,titleBar + (fontLineSpacing * (row-topRow).toFloat())-padding, textPaint)
        }
        moveToSavedCursor(canvas, cursor, showCursor, padding)
    }

    private fun moveToSavedCursor(canvas: Canvas, cursor: TerminalView.Cursor, showCursor: Boolean, pad: Int) {
        if(showCursor) {
            canvas.drawRect((cursor.x) * fontWidth.toFloat(), titleBar + (fontLineSpacing * (cursor.y - 1))-pad.toFloat(),
                     (cursor.x + 1) * fontWidth.toFloat(), titleBar + (fontLineSpacing * cursor.y).toFloat()-pad, textPaint)
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