package com.yokoro.terminal_lib.repository

import arrow.core.Either
import arrow.core.Left
import arrow.core.None
import arrow.core.Right
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.entity.Terminal
import com.yokoro.terminal_lib.entity.TerminalRow

/**
 * データの保存，取得をおこなう
 *
 */
class TerminalRepository: ITerminalRepository {
    private lateinit var terminal: Terminal

    private fun <T> getOrError(right: T): Either<Failure, T> =
        when (::terminal.isInitialized) {
            true -> Left(Failure.UninitializedException)
            else -> Right(right)
        }

    private fun <T> handleOrError(right: T, f: () -> Unit): Either<Failure, T> =
        when (::terminal.isInitialized) {
            true -> Left(Failure.UninitializedException)
            else -> {
                f()
                Right(right)
            }
        }

    override fun createTerminal(ss: ScreenSize): Either<Failure, None> {
        this.terminal = Terminal(ss)
        addRow(false)
        return Right(None)
    }

    override fun getScreenSize(): Either<Failure, ScreenSize> =
        getOrError(terminal.screenSize)

    override fun addRow(lineWarp: Boolean): Either<Failure, Terminal> =
        handleOrError(this.terminal) {
            TerminalRow(
                CharArray(terminal.screenSize.columns) { ' ' },
                IntArray(terminal.screenSize.columns) { 0 },
                lineWarp
            )
        }

    override fun setText(x: Int, y: Int, text: Char): Either<Failure, Terminal> =
        handleOrError(this.terminal) {
            this.terminal.terminalBuffer[y].text[x] = text
        }

    override fun setColor(x: Int, y: Int, color: Int): Either<Failure, Terminal> =
        handleOrError(this.terminal) {
            this.terminal.terminalBuffer[y].color[x] = color
        }

    override fun getTerminalBuffer(): Either<Failure, ArrayList<TerminalRow>> =
        getOrError(terminal.terminalBuffer)

    override fun resize(
       newScreenSize: ScreenSize
    ): Either<Failure, Terminal> {
        if (::terminal.isInitialized) {
            return Left(Failure.UninitializedException)
        }

        var oldX = 0
        var oldY = 0
        var newY = 0  // newTextBuffer の index
        var lineWarp: Boolean
        val newTextBuffer: ArrayList<TerminalRow> = ArrayList()

        newTextBuffer.add(
            TerminalRow(
                CharArray(newScreenSize.rows){' '},
                IntArray(newScreenSize.rows){0},
                false
            )
        )

        for (y in 0 until terminal.terminalBuffer.size){
            for (newX in 0 until terminal.screenSize.columns){
                newTextBuffer[newY].text[newX] = terminal.terminalBuffer[oldY].text[oldX]
                oldX++

                // oldX が 行文字数に
                if (oldX == terminal.screenSize.columns){
                    // 次の行に移動
                    oldX = 0
                    if(!terminal.terminalBuffer[oldY].lineWrap){
                        oldY++
                        break
                    }
                    oldY++
                }
                if (oldY == terminal.terminalBuffer.size){
                    break
                }
            }
            if (oldY == terminal.terminalBuffer.size){
                break
            }
            newY++
            lineWarp = oldX != terminal.screenSize.columns
            newTextBuffer.add(
                TerminalRow(
                    CharArray(newScreenSize.rows){' '},
                    IntArray(newScreenSize.rows){0},
                    lineWarp
                )
            )
        }

        terminal.terminalBuffer.clear()
        terminal.terminalBuffer = newTextBuffer
        terminal.screenSize = newScreenSize

        return Right(terminal)
    }

    override fun getTopRow(): Either<Failure, Int> =
        getOrError(terminal.topRow)

    override fun setTopRow(n: Int): Either<Failure, None> =
        handleOrError(None) {
            terminal.topRow = n
        }
}