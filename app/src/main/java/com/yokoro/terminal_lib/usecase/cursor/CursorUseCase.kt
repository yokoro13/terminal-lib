package com.yokoro.terminal_lib.usecase.cursor

import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.usecase.cursor.*

class CursorUseCase (
    private val moveDown: MoveDown,
    private val moveLeft: MoveLeft,
    private val moveRight: MoveRight,
    private val moveUp: MoveUp,
    private val setCursor: SetCursor
    ): ICursorUseCase {

    override suspend fun setCursor(cursor: Cursor, ss: ScreenSize, x: Int, y: Int) {
        setCursor.run(SetCursor.Params(cursor, ss, x, y))
    }

    override suspend fun moveUp(cursor: Cursor, ss: ScreenSize, n: Int) {
        moveUp.run(MoveUp.Params(cursor, ss, n))
    }

    override suspend fun moveDown(cursor: Cursor, ss: ScreenSize, n: Int) {
        moveDown.run(MoveDown.Params(cursor, ss, n))
    }

    override suspend fun moveRight(cursor: Cursor, ss: ScreenSize, n: Int) {
        moveRight.run(MoveRight.Params(cursor, ss, n))
    }

    override suspend fun moveLeft(cursor: Cursor, ss: ScreenSize, n: Int) {
        moveLeft.run(MoveLeft.Params(cursor, ss, n))
    }
}