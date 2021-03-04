package com.yokoro.terminal_lib.repository

import arrow.core.Either
import arrow.core.Right
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize

class CursorRepository: ICursorRepository {
    override fun moveTo(c: Cursor, ss: ScreenSize, x: Int, y: Int): Either<Failure, Cursor> =
        Right(setY(setX(c, ss, x), ss, y))

    override fun moveDown(c: Cursor, ss: ScreenSize, n: Int): Either<Failure, Cursor> =
        Right(setY(c, ss, c.y + n))

    override fun moveUp(c: Cursor, ss: ScreenSize, n: Int): Either<Failure, Cursor> =
        Right(setY(c, ss, c.y - n))

    override fun moveLeft(c: Cursor, ss: ScreenSize, n: Int): Either<Failure, Cursor> =
        Right(setX(c, ss, c.x - n))

    override fun moveRight(c: Cursor, ss: ScreenSize, n: Int): Either<Failure, Cursor> =
        Right(setX(c, ss, c.x + n))

    private fun setX(c: Cursor, ss: ScreenSize, x: Int): Cursor =
        when {
            (x < 0) -> Cursor(0, c.y)
            (ss.columns <= x) -> Cursor(ss.columns - 1, c.y)
            else -> Cursor(x, c.y)
        }

    private fun setY(c: Cursor, ss: ScreenSize, y: Int): Cursor =
        when {
            (y < 0) -> Cursor(c.x, 0)
            (ss.rows <= y) -> Cursor(c.x, ss.rows - 1)
            else -> Cursor(c.x, y)
        }
}