package com.yokoro.terminal_lib.repository

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.entity.Cursor

interface CursorRepository {
    fun moveTo(x: Int, y: Int): Either<Failure, Cursor>

    fun moveDown(n: Int): Either<Failure, Cursor>
    fun moveUp(n: Int): Either<Failure, Cursor>
    fun moveLeft(n: Int): Either<Failure, Cursor>
    fun moveRight(n: Int): Either<Failure, Cursor>
}