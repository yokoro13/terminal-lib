package com.yokoro.terminal_lib.usecase.terminal

import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize

interface ITerminalUseCase {

    suspend fun addNewRow(warp: Boolean)

    suspend fun createTerminalBuffer()

    suspend fun resize()

    suspend fun getScreenSize(): ScreenSize

    suspend fun getTopRow(): Int

    suspend fun setTopRow(n: Int)

    suspend fun inputText(cursor: Cursor, text: Char)

    suspend fun inputColor(cursor: Cursor, color: Int)
}