package usecase.terminal

import entity.Cursor
import entity.ScreenSize

interface ITerminalUseCase {
    suspend fun createTerminal(screenSize: ScreenSize)

    suspend fun resize(newScreenSize: ScreenSize)

    suspend fun getScreenSize(): ScreenSize

    suspend fun getTopRow(): Int

    suspend fun setTopRow(n: Int)

    suspend fun inputText(cursor: Cursor, text: Char)

    suspend fun inputColor(cursor: Cursor, color: Int)

}