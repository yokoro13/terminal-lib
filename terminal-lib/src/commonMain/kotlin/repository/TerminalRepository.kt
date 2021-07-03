package repository

import core.Either
import core.Either.*
import core.Failure
import core.UseCase.None
import entity.Cursor
import entity.ScreenSize
import com.yokoro.terminal_lib.entity.Terminal
import entity.TerminalArray
import entity.TerminalChar

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
        addRow(-1)
        return Right(None())
    }

    override fun getScreenSize(): Either<Failure, ScreenSize> =
        getOrError(terminal.screenSize)

    private fun getEmptyTerminalArray(size: Int): ArrayList<TerminalChar> {
        val emptyList: ArrayList<TerminalChar> = ArrayList()
        for (i in 0 until size) {
            emptyList.add(TerminalChar(' ', 0, 0))
        }
        return emptyList
    }

    override fun addRow(lineWarpPos: Int): Either<Failure, Terminal> =
        handleOrError(this.terminal) {
            terminal.terminalBuffer.add(
                TerminalArray(
                    getEmptyTerminalArray(terminal.screenSize.columns),
                    lineWarpPos
                )
            )
        }

    override fun setTerminalChar(x: Int, y: Int, terminalChar: TerminalChar): Either<Failure, Terminal> =
        handleOrError(this.terminal) {
            this.terminal.terminalBuffer[y].terminalRow[x] = terminalChar
            if (terminal.terminalBuffer[y].lineWrapPos < x) {
                terminal.terminalBuffer[y].lineWrapPos = x
            }
        }


    override fun setChar(x: Int, y: Int, text: Char): Either<Failure, Terminal> =
        handleOrError(this.terminal) {
            this.terminal.terminalBuffer[y].terminalRow[x].char = text
            if (terminal.terminalBuffer[y].lineWrapPos < x) {
                terminal.terminalBuffer[y].lineWrapPos = x
            }
        }

    override fun setForeColor(x: Int, y: Int, color: Int): Either<Failure, Terminal> =
        handleOrError(this.terminal) {
            this.terminal.terminalBuffer[y].terminalRow[x].foreColor = color
        }

    override fun setBackColor(x: Int, y: Int, color: Int): Either<Failure, Terminal> =
        handleOrError(this.terminal) {
            this.terminal.terminalBuffer[y].terminalRow[x].backColor = color
        }

    override fun getTerminalBuffer(): Either<Failure, ArrayList<TerminalArray>> =
        getOrError(terminal.terminalBuffer)

    private fun <T> concat(a1: ArrayList<T>, a2: ArrayList<T>): ArrayList<T> {
        a2.forEach { a1.add(it) }
        return a1
    }

    private fun TerminalArray.addEmptyToScreenColumnSize(column: Int) {
        val size = this.terminalRow.size
        for (i in size until column) {
            this.terminalRow.add(TerminalChar(' ', 0, 0))
        }
    }

    override fun resize(
       newScreenSize: ScreenSize
    ): Either<Failure, Terminal> {
        if (!::terminal.isInitialized) {
            return Left(Failure.UninitializedException)
        }

        // 横幅が同じときは縦幅を縮めるだけ
        if (terminal.screenSize.columns == newScreenSize.columns) {
            val currentRow = terminal.topRow + terminal.cursor.y
            terminal.screenSize.rows = newScreenSize.rows
            terminal.topRow = if (currentRow - newScreenSize.rows >= 0) currentRow - newScreenSize.rows + 1 else 0
            terminal.cursor = Cursor(terminal.cursor.x, currentRow - terminal.topRow)
            return Right(terminal)
        }

        val newBuffer: ArrayList<TerminalArray> = ArrayList()
        val oldBuffer = terminal.terminalBuffer

        var writingLine = 0

        for (row in 0 until oldBuffer.size) {

            if (oldBuffer[row].lineWrapPos == -1) {
                if (row != 0) {
                    concat(newBuffer[writingLine].terminalRow, oldBuffer[row].terminalRow)
                    terminal.cursor.y--
                } else {
                    newBuffer.add(oldBuffer[row])
                }
            } else {
                newBuffer.add(
                    TerminalArray(
                        oldBuffer[row].terminalRow.subList(
                            0,
                            oldBuffer[row].lineWrapPos+1
                        ) as ArrayList<TerminalChar>,
                        oldBuffer[row].lineWrapPos
                    )
                )
            }

            val overed = newBuffer[writingLine].terminalRow.size - newScreenSize.columns

            if (0 < overed) {
                newBuffer[writingLine].lineWrapPos = -1
                val usedLine = overed / newScreenSize.columns + 1

                for (i in 0 until usedLine) {
                    val overedParts = newBuffer[writingLine].terminalRow.subList(
                        newScreenSize.columns,
                        newBuffer[writingLine].terminalRow.size
                    ) as ArrayList<TerminalChar>

                    if (overedParts.size == 0) {
                        break
                    }

                    newBuffer[writingLine].terminalRow = newBuffer[writingLine].terminalRow.subList(
                        0,
                        newScreenSize.columns
                    ) as ArrayList<TerminalChar>

                    newBuffer.add(TerminalArray(overedParts, -1))
                    writingLine++
                }
            }

        }

        newBuffer.forEach {
            it.addEmptyToScreenColumnSize(newScreenSize.columns)
            if (it.lineWrapPos != -1) {
                it.lineWrapPos = it.terminalRow.size
            }
        }

        terminal.terminalBuffer = newBuffer
        terminal.screenSize = newScreenSize

        val currentLine = if (newBuffer.size - newScreenSize.rows + terminal.cursor.y > 0) {
            newBuffer.size - newScreenSize.rows + terminal.cursor.y
        } else 0
        terminal.topRow = if (currentLine - newScreenSize.rows >= 0) currentLine - newScreenSize.rows + 1 else 0
        terminal.cursor = Cursor(newBuffer[currentLine].terminalRow.size - 1, currentLine - terminal.topRow)

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