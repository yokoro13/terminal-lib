package com.yokoro.terminal_lib.repository

import arrow.core.Either
import arrow.core.None
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.entity.Terminal
import com.yokoro.terminal_lib.entity.TerminalRow

interface ITerminalRepository {

    fun createTerminal(ss: ScreenSize): Either<Failure, None>

    /**
     * get setting screen size
     */
    fun getScreenSize(): Either<Failure, ScreenSize>

    /**
     * add new row.
     */
    fun addRow(lineWarp: Boolean = false): Either<Failure, Terminal>

    /**
     * set text at (column: x, row: y)
     *
     * @param x : column at y
     * @param y : row
     * @param text : set text
     */
    fun setText(x: Int, y: Int, text: Char): Either<Failure, Terminal>

    /**
     * set color at (column: x, row: y)
     *
     * @param x : column at y
     * @param y : row
     * @param color : color code
     */
    fun setColor(x: Int, y: Int, color: Int): Either<Failure, Terminal>

    /**
     * get row CharArray at y
     *
     * @param y : row
     * @return row CharArray
     */
    fun getTerminalBuffer(): Either<Failure, ArrayList<TerminalRow>>

    /**
     * resize buffer size
     *
     * @param newScreenColumnSize:
     * @param newScreenRowSize:
     */
    fun resize(newScreenColumnSize: Int, newScreenRowSize: Int): Either<Failure, Terminal>

    fun getTopRow(): Either<Failure, Int>

    fun setTopRow(n: Int): Either<Failure, None>

}