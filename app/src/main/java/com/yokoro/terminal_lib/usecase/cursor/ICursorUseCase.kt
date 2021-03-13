package com.yokoro.terminal_lib.usecase.cursor

import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize

interface ICursorUseCase {
    suspend fun setCursor(cursor: Cursor, ss: ScreenSize, x: Int, y: Int)
    suspend fun moveUp(cursor: Cursor, ss: ScreenSize, n: Int)
    suspend fun moveDown(cursor: Cursor, ss: ScreenSize, n: Int)
    suspend fun moveRight(cursor: Cursor, ss: ScreenSize, n: Int)
    suspend fun moveLeft(cursor: Cursor, ss: ScreenSize, n: Int)
}