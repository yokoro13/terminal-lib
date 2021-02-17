package com.yokoro.terminal_lib.repository

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.entity.TerminalBuffer

interface TerminalRepository {
    /**
     * add new row.
     */
    fun addRow(lineWarp: Boolean = false): Either<Failure, TerminalBuffer>

    /**
     * set text at (column: x, row: y)
     *
     * @param x : column at y
     * @param y : row
     * @param text : set text
     */
    fun setText(x: Int, y: Int, text: Char): Either<Failure, TerminalBuffer>

    /**
     * set color at (column: x, row: y)
     *
     * @param x : column at y
     * @param y : row
     * @param color : color code
     */
    fun setColor(x: Int, y: Int, color: Int): Either<Failure, TerminalBuffer>

    /**
     * get row CharArray at y
     *
     * @param y : row
     * @return row CharArray
     */
    fun getRowCharArrayAt(y: Int): Either<Failure, CharArray>

    /**
     * resize buffer size
     *
     * @param newScreenColumnSize:
     * @param newScreenRowSize:
     */
    fun resize(newScreenColumnSize: Int, newScreenRowSize: Int): Either<Failure, TerminalBuffer>

}