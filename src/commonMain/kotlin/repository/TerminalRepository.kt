package repository

import core.Either
import core.Either.*
import core.Failure
import core.UseCase.None
import entity.Cursor
import entity.ScreenSize
import com.yokoro.terminal_lib.entity.Terminal
import com.yokoro.terminal_lib.repository.ITerminalRepository
import entity.TerminalRow

/**
 * データの保存，取得をおこなう
 *
 */
class TerminalRepository: ITerminalRepository {
    private lateinit var terminal: Terminal

    private fun <T> getOrError(right: T): Either<Failure, T> =
        when (::terminal.isInitialized) {
            false -> Left(Failure.UninitializedException)
            else -> Right(right)
        }

    private fun <T> handleOrError(right: T, f: () -> Unit): Either<Failure, T> =
        when (::terminal.isInitialized) {
            false -> Left(Failure.UninitializedException)
            else -> {
                f()
                Right(right)
            }
        }

    override fun createTerminal(ss: ScreenSize): Either<Failure, None> {
        this.terminal = Terminal(ss)
        addRow(false)
        return Right(None())
    }

    override fun getScreenSize(): Either<Failure, ScreenSize> =
        getOrError(terminal.screenSize)

    override fun addRow(lineWarp: Boolean): Either<Failure, Terminal> =
        handleOrError(this.terminal) {
            terminal.terminalBuffer.add(
                TerminalRow(
                    CharArray(terminal.screenSize.columns) { ' ' },
                    IntArray(terminal.screenSize.columns) { 0 },
                    lineWarp
                )
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
                CharArray(newScreenSize.columns){' '},
                IntArray(newScreenSize.columns){0},
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
                    CharArray(newScreenSize.columns){' '},
                    IntArray(newScreenSize.columns){0},
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
        handleOrError(None()) {
            terminal.topRow = n
        }

    override fun setCursor(x: Int, y: Int): Either<Failure, None> =
        handleOrError(None()) {
            setX(x)
            setY(y)
        }

    override fun getCursor(): Either<Failure, Cursor> =
        getOrError(terminal.cursor)

    override fun setDisplayingState(state: Boolean): Either<Failure, None> =
        handleOrError(None()) {
            terminal.cursor.isDisplaying = state
        }

    override fun isDisplaying(): Either<Failure, Boolean> =
        getOrError(terminal.cursor.isDisplaying)

    private fun setX(x: Int) {
        terminal.cursor =
            when {
                (x < 0) -> Cursor(0, terminal.cursor.y)
                (terminal.screenSize.columns <= x) -> Cursor(terminal.screenSize.columns - 1, terminal.cursor.y)
                else -> Cursor(x, terminal.cursor.y)
            }
    }

    private fun setY(y: Int) {
        terminal.cursor =
            when {
                (y < 0) -> Cursor(terminal.cursor.x, 0)
                (terminal.screenSize.rows <= y) -> Cursor(terminal.cursor.x, terminal.screenSize.rows - 1)
                else -> Cursor(terminal.cursor.x, y)
            }
    }

}