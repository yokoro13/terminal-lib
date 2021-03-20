package com.yokoro.terminal_lib.usecase.cursor

import arrow.core.None
import arrow.core.getOrElse
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize

class CursorUseCase (
    private val setCursor: SetCursor,
    private val getCursor: GetCursor
    ): ICursorUseCase {

    override suspend fun setCursor(x: Int, y: Int) {
        setCursor.run(SetCursor.Params(x, y))
    }

    override suspend fun moveUp(cursor: Cursor, ss: ScreenSize, n: Int) {
        setCursor.run(SetCursor.Params(getCursor().x, getCursor().y - n))
    }

    override suspend fun moveDown(cursor: Cursor, ss: ScreenSize, n: Int) {
        setCursor.run(SetCursor.Params(getCursor().x, getCursor().y + n))
    }

    override suspend fun moveRight(cursor: Cursor, ss: ScreenSize, n: Int) {
        setCursor.run(SetCursor.Params(getCursor().x + n, getCursor().y))
    }

    override suspend fun moveLeft(cursor: Cursor, ss: ScreenSize, n: Int) {
        setCursor.run(SetCursor.Params(getCursor().x - n, getCursor().y))
    }

    override suspend fun getCursor(): Cursor =
        getCursor.run(None).getOrElse { throw IllegalArgumentException("") }
}