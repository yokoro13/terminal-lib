package com.yokoro.terminal_lib.usecase.escapesequence

import arrow.core.getOrElse
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.entity.TerminalRow
import com.yokoro.terminal_lib.repository.ICursorRepository
import com.yokoro.terminal_lib.repository.ITerminalRepository

class EscapeSequenceInteractor(
    private var cursorRepository: ICursorRepository,
    private var terminalRepository: ITerminalRepository
): EscapeSequenceUseCase {
    private fun getScreenSize(): ScreenSize =
        terminalRepository.getScreenSize().getOrElse { throw IllegalArgumentException("") }

    private fun getTextBuffer(): List<TerminalRow> =
        terminalRepository.getTextBuffer().getOrElse { throw IllegalArgumentException("") }

    private fun getCurrentRow(currCursor: Cursor): Int =
        getTopRow() + currCursor.y

    private fun getTopRow(): Int =
        terminalRepository.getTopRow().getOrElse { throw IllegalArgumentException("") }

    override fun moveRight(cursor: Cursor, n: Int) {
        cursorRepository.moveRight(cursor, getScreenSize(), n)
    }

    override fun moveLeft(cursor: Cursor, n: Int) {
        cursorRepository.moveLeft(cursor, getScreenSize(), -n)
    }

    override fun moveUp(cursor: Cursor, n: Int) {
        cursorRepository.moveDown(cursor, getScreenSize(), -n)
    }

    override fun moveDown(cursor: Cursor, n: Int) {
        cursorRepository.moveDown(cursor, getScreenSize(), n)

        if (getTextBuffer().size <= getCurrentRow(cursor)) {
            for (i in 0 .. getCurrentRow(cursor) - getTextBuffer().size) {
                terminalRepository.addRow()
            }
        }
    }

    override fun moveUpToLineHead(cursor: Cursor, n: Int) {
        cursorRepository.moveTo(cursor, getScreenSize(), 0, cursor.y)
        moveUp(cursor, n)
    }

    override fun moveDownToLineHead(cursor: Cursor, n: Int) {
        cursorRepository.moveTo(cursor, getScreenSize(), 0, cursor.y)
        moveDown(cursor, n)
    }

    override fun moveCursor(cursor: Cursor, n: Int) {
        cursorRepository.moveTo(cursor, getScreenSize(), n, cursor.y)
    }

    override fun moveCursor(cursor: Cursor, n: Int, m: Int) {

        if(n-1 > cursor.y) {
            cursor.y = 0
            moveDown(cursor, n-1)
        } else {
            cursor.y = n-1
        }

        cursor.x = m-1

    }


    private fun clearScreenFromCursor(cursor: Cursor) {
        clearLineFrom(getCurrentRow(cursor), cursor.x)
        for (y in cursor.y+1 until getScreenSize().rows) {
            if (getCurrentRow(cursor) - cursor.y + y <= getTextBuffer().size) {
                break
            }
            clearLineFrom(getCurrentRow(cursor) - cursor.y + y, 0)
        }
    }

    private fun clearScreenToCursor(cursor: Cursor){
        clearLineTo(getCurrentRow(cursor) + cursor.y, cursor.x)
        for (y in 0 until cursor.y) {
            clearLineFrom(getCurrentRow(cursor) - cursor.y + y, 0)
        }
    }


    override fun clearScreen(cursor: Cursor, n: Int) {
        when (n) {
            0 -> clearScreenFromCursor(cursor)
            1 -> clearScreenToCursor(cursor)
            2 -> {
                clearScreenFromCursor(Cursor(0, 0))
            }
        }
    }

    private fun clearLineFrom(line: Int, from: Int) {
        for (x in from until getScreenSize().columns) {
            terminalRepository.setText(x, line, ' ')
        }
    }

    private fun clearLineTo(line: Int, to: Int) {
        for (x in 0 until to) {
            terminalRepository.setText(x, line, ' ')
        }
    }

    override fun clearLine(cursor: Cursor, n: Int) {
        when (n) {
            0 -> clearLineFrom(getCurrentRow(cursor), cursor.x)
            1 -> clearLineTo(getCurrentRow(cursor), cursor.x)
            2 -> {
                clearLineFrom(getCurrentRow(cursor), 0)
            }
        }
    }

    override fun scrollNext(n: Int) {
        if (getTopRow() + n > getTextBuffer().size) return  //一番したに空白追加？？
         terminalRepository.setTopRow(getTopRow() + n)
    }

    override fun scrollBack(n: Int) {

        //一番上に追加で一番した削除？？？(あくまで画面上でスクロールしていると見せかけている?)
        if (getTopRow() - n < 0)
            terminalRepository.setTopRow(0)
        else
            terminalRepository.setTopRow(getTopRow() - n)
    }

    override fun selectGraphicRendition(n: Int) {
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