package com.yokoro.terminal_lib.repository

import core.Failure
import entity.Cursor
import entity.ScreenSize
import com.yokoro.terminal_lib.entity.Terminal
import core.Either
import core.UseCase.None
import entity.TerminalRow

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
     * get terminalBuffer
     *
     * @return row CharArray
     */
    fun getTerminalBuffer(): Either<Failure, ArrayList<TerminalRow>>

    /**
     * resize buffer size
     *
     * @param newScreenSize
     */
    fun resize(newScreenSize: ScreenSize): Either<Failure, Terminal>

    fun getTopRow(): Either<Failure, Int>

    fun setTopRow(n: Int): Either<Failure, None>

    fun setCursor(x: Int, y: Int): Either<Failure, None>

    fun getCursor(): Either<Failure, Cursor>

    fun setDisplayingState(state: Boolean): Either<Failure, None>

    fun isDisplaying(): Either<Failure, Boolean>

}