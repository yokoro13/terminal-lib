package com.yokoro.terminal_lib.usecase.escapesequence

import arrow.core.None
import arrow.core.getOrElse
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.entity.TerminalRow
import com.yokoro.terminal_lib.usecase.cursor.ICursorUseCase
import com.yokoro.terminal_lib.usecase.terminalbuffer.ITerminalBufferUseCase
import com.yokoro.terminal_lib.usecase.terminal.GetScreenSize
import com.yokoro.terminal_lib.usecase.terminal.GetTopRow
import com.yokoro.terminal_lib.usecase.terminal.SetTopRow

class EscapeSequenceUseCase(
    private val getScreenSize: GetScreenSize,
    private val getTopRow: GetTopRow,
    private val setTopRow: SetTopRow,
    private val cursorUseCase: ICursorUseCase,
    private val terminalBufferUseCase: ITerminalBufferUseCase
) : IEscapeSequenceUseCase {

    private suspend fun getScreenSize(): ScreenSize =
        getScreenSize.run(None).getOrElse { throw IllegalArgumentException("") }

    private suspend fun getTerminalBuffer(): ArrayList<TerminalRow> =
        terminalBufferUseCase.getTerminalBuffer()

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

        if (getTerminalBuffer().size <= getCurrentRow(cursor)) {
            for (i in 0 .. getCurrentRow(cursor) - getTerminalBuffer().size) {
                terminalBufferUseCase.addNewRow(false)
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
            if (getCurrentRow(cursor) - cursor.y + y <= getTerminalBuffer().size) {
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
            terminalBufferUseCase.setText(x, line, ' ')
        }
    }

    private suspend fun clearLineTo(line: Int, to: Int) {
        for (x in 0 until to) {
            terminalBufferUseCase.setText(x, line, ' ')
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
        if (getTopRow() + n > getTerminalBuffer().size) return  //一番したに空白追加？？
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