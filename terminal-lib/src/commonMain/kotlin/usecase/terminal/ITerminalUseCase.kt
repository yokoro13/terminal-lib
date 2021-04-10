package usecase.terminal

import entity.Cursor
import entity.ScreenSize

interface ITerminalUseCase {
    fun createTerminal(screenSize: ScreenSize)

    fun resize(newScreenSize: ScreenSize)

    fun getScreenSize(): ScreenSize

    fun getTopRow(): Int

    fun setTopRow(n: Int)

    fun inputText(cursor: Cursor, text: Char)

    fun inputColor(cursor: Cursor, color: Int)

}