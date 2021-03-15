package com.yokoro.terminal_lib.usecase.escapesequence

import arrow.core.None
import arrow.core.getOrElse
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.entity.TerminalRow
import com.yokoro.terminal_lib.usecase.cursor.ICursorUseCase
import com.yokoro.terminal_lib.usecase.terminal.*

class EscapeSequenceUseCase(
    private val addNewRow: AddNewRow,
    private val getTextBuffer: GetTextBuffer,
    private val setColor: SetColor,
    private val setText: SetText,
    private val getScreenSize: GetScreenSize,
    private val getTopRow: GetTopRow,
    private val setTopRow: SetTopRow,
    private val cursorUseCase: ICursorUseCase
) : IEscapeSequenceUseCase {

    private suspend fun getScreenSize(): ScreenSize =
        getScreenSize.run(None).getOrElse { throw IllegalArgumentException("") }

    private suspend fun getTextBuffer(): List<TerminalRow> =
        getTextBuffer.run(None).getOrElse { throw IllegalArgumentException("") }

    private suspend fun getTopRow(): Int =
        getTopRow.run(None).getOrElse { throw IllegalArgumentException("") }

    private suspend fun getCurrentRow(currCursor: Cursor): Int =
        getTopRow() + currCursor.y

    override suspend fun moveRight(cursor: Cursor, n: Int) {
        cursorUseCase.moveRight(cursor, getScreenSize(), n)
    }

    override suspend fun moveLeft(cursor: Cursor, n: Int) {
        cursorUseCase.moveLeft(cursor, getScreenSize(), -n)
    }

    override suspend fun moveUp(cursor: Cursor, n: Int) {
        cursorUseCase.moveDown(cursor, getScreenSize(), -n)
    }

    override suspend fun moveDown(cursor: Cursor, n: Int) {
        cursorUseCase.moveDown(cursor, getScreenSize(), n)

        if (getTextBuffer().size <= getCurrentRow(cursor)) {
            for (i in 0 .. getCurrentRow(cursor) - getTextBuffer().size) {
                addNewRow.run(AddNewRow.Params())
            }
        }
    }

    override suspend fun moveUpToLineHead(cursor: Cursor, n: Int) {
        cursorUseCase.setCursor(cursor, getScreenSize(), 0, cursor.y)
        moveUp(cursor, n)
    }

    override suspend fun moveDownToLineHead(cursor: Cursor, n: Int) {
        cursorUseCase.setCursor(cursor, getScreenSize(), 0, cursor.y)
        moveDown(cursor, n)
    }

    override suspend fun moveCursor(cursor: Cursor, n: Int) {
        cursorUseCase.setCursor(cursor, getScreenSize(), n, cursor.y)
    }

    override suspend fun moveCursor(cursor: Cursor, n: Int, m: Int) {

        if(n-1 > cursor.y) {
            cursor.y = 0
            moveDown(cursor, n-1)
        } else {
            cursor.y = n-1
        }

        cursor.x = m-1

    }

    private suspend fun clearScreenFromCursor(cursor: Cursor) {
        clearLineFrom(getCurrentRow(cursor), cursor.x)
        for (y in cursor.y+1 until getScreenSize().rows) {
            if (getCurrentRow(cursor) - cursor.y + y <= getTextBuffer().size) {
                break
            }
            clearLineFrom(getCurrentRow(cursor) - cursor.y + y, 0)
        }
    }

    private suspend fun clearScreenToCursor(cursor: Cursor){
        clearLineTo(getCurrentRow(cursor) + cursor.y, cursor.x)
        for (y in 0 until cursor.y) {
            clearLineFrom(getCurrentRow(cursor) - cursor.y + y, 0)
        }
    }

    override suspend fun clearScreen(cursor: Cursor, n: Int) {
        when (n) {
            0 -> clearScreenFromCursor(cursor)
            1 -> clearScreenToCursor(cursor)
            2 -> {
                clearScreenFromCursor(Cursor(0, 0))
            }
        }
    }

    private suspend fun clearLineFrom(line: Int, from: Int) {
        for (x in from until getScreenSize().columns) {
            setText.run(SetText.Params(x, line, ' '))
        }
    }

    private suspend fun clearLineTo(line: Int, to: Int) {
        for (x in 0 until to) {
            setText.run(SetText.Params(x, line, ' '))
        }
    }

    override suspend fun clearLine(cursor: Cursor, n: Int) {
        when (n) {
            0 -> clearLineFrom(getCurrentRow(cursor), cursor.x)
            1 -> clearLineTo(getCurrentRow(cursor), cursor.x)
            2 -> {
                clearLineFrom(getCurrentRow(cursor), 0)
            }
        }
    }

    override suspend fun scrollNext(n: Int) {
        if (getTopRow() + n > getTextBuffer().size) return  //一番したに空白追加？？
        setTopRow.run(SetTopRow.Params(getTopRow() + n))
    }

    override suspend fun scrollBack(n: Int) {

        //一番上に追加で一番した削除？？？(あくまで画面上でスクロールしていると見せかけている?)
        if (getTopRow() - n < 0)
            setTopRow.run(SetTopRow.Params(0))
        else
            setTopRow.run(SetTopRow.Params(getTopRow() - n))
    }

    override suspend fun selectGraphicRendition(n: Int) {
        /*
        termBuffer.isColorChange = true
        when (n) {
            0, 39, 30 -> termBuffer.charColor = 0xFF000000.toInt()
            31 -> termBuffer.charColor = 0xFFff0000.toInt()
            32 -> termBuffer.charColor = 0xFF008000.toInt()
            33 -> termBuffer.charColor = 0xFFFFFF00.toInt()
            34 -> termBuffer.charColor = 0xFF0000FF.toInt()
            35 -> termBuffer.charColor = 0xFFFF00FF.toInt()
            36 -> termBuffer.charColor = 0xFF00FFFF.toInt()
            37 -> termBuffer.charColor = 0xFFFFFFFF.toInt()
        }
         */
    }
}