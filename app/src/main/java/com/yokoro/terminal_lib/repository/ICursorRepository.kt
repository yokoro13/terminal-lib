package com.yokoro.terminal_lib.repository

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize

interface ICursorRepository {
    fun moveTo(c: Cursor, ss: ScreenSize, x: Int, y: Int): Either<Failure, Cursor>

    fun moveDown(c: Cursor, ss: ScreenSize, n: Int): Either<Failure, Cursor>
    fun moveUp(c: Cursor, ss: ScreenSize, n: Int): Either<Failure, Cursor>
    fun moveLeft(c: Cursor, ss: ScreenSize, n: Int): Either<Failure, Cursor>
    fun moveRight(c: Cursor, ss: ScreenSize, n: Int): Either<Failure, Cursor>
}