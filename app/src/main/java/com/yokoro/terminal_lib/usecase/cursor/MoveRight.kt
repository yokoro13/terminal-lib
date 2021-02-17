package com.yokoro.terminal_lib.usecase.cursor

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.repository.CursorRepository
import javax.inject.Inject

class MoveRight
@Inject constructor(
    private val cursorRepository: CursorRepository
): UseCase<Cursor, MoveRight.Params>() {

    override suspend fun run(params: Params): Either<Failure, Cursor> =
        cursorRepository.moveRight(params.n)

    data class Params(val n: Int)
}