package com.yokoro.terminal_lib.usecase.cursor

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.repository.ICursorRepository
import javax.inject.Inject

class MoveLeft
@Inject constructor(
    private val ICursorRepository: ICursorRepository
): UseCase<Cursor, MoveLeft.Params>() {

    override suspend fun run(params: Params): Either<Failure, Cursor> =
        ICursorRepository.moveLeft(params.c, params.ss, params.n)

    data class Params(val c: Cursor, val ss: ScreenSize, val n: Int)
}