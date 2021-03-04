package com.yokoro.terminal_lib.repository

import arrow.core.Either
import arrow.core.Right
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize

class CursorRepository: ICursorRepository {
    override fun moveTo(c: Cursor, ss: ScreenSize, x: Int, y: Int): Either<Failure, Cursor> {
        return Right(Cursor(x, y))
    }

    override fun moveDown(c: Cursor, ss: ScreenSize, n: Int): Either<Failure, Cursor> {
        return Right(Cursor(c.x, c.y + n))
    }

    override fun moveUp(c: Cursor, ss: ScreenSize, n: Int): Either<Failure, Cursor> {
        TODO("Not yet implemented")
    }

    override fun moveLeft(c: Cursor, ss: ScreenSize, n: Int): Either<Failure, Cursor> {
        TODO("Not yet implemented")
    }

    override fun moveRight(c: Cursor, ss: ScreenSize, n: Int): Either<Failure, Cursor> {
        TODO("Not yet implemented")
    }
}