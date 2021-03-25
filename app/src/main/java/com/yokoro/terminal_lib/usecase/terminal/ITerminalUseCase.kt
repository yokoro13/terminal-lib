package com.yokoro.terminal_lib.usecase.terminal

import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize

interface ITerminalUseCase {
    suspend fun createTerminal(screenSize: ScreenSize)

    suspend fun resize(newScreenSize: ScreenSize)

    suspend fun getScreenSize(): ScreenSize

    suspend fun getTopRow(): Int

    suspend fun setTopRow(n: Int)

    suspend fun inputText(cursor: Cursor, text: Char)

    suspend fun inputColor(cursor: Cursor, color: Int)

    suspend fun setCurrentRow(n: Int)

    suspend fun getCurrentRow(): Int
}