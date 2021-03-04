package com.yokoro.terminal_lib.usecase.escapesequence

import com.yokoro.terminal_lib.entity.Cursor

interface EscapeSequenceUseCase {
    fun moveRight(cursor: Cursor, n: Int)
    fun moveLeft(cursor: Cursor, n: Int)
    fun moveUp(cursor: Cursor, n: Int)
    fun moveDown(cursor: Cursor, n: Int)
    fun moveUpToRowLead(cursor: Cursor, n: Int)
    fun moveDownToRowLead(cursor: Cursor, n: Int)
    fun moveCursor(cursor: Cursor, n: Int)
    fun moveCursor(cursor: Cursor, n: Int, m: Int)
    fun clearDisplay(cursor: Cursor, n: Int)
    fun clearRow(cursor: Cursor, n: Int)
    fun scrollNext(n: Int)
    fun scrollBack(n: Int)
    fun selectGraphicRendition(n: Int)
}