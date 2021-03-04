package com.yokoro.terminal_lib.usecase.cursor

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.repository.ICursorRepository
import javax.inject.Inject

class SetCursor
@Inject constructor(
    private val ICursorRepository: ICursorRepository
): UseCase<Cursor, SetCursor.Params>() {

    override suspend fun run(params: Params): Either<Failure, Cursor> =
        ICursorRepository.moveTo(params.c, params.ss, params.x, params.y,)

    data class Params(val c: Cursor, val ss: ScreenSize, val x: Int, val y: Int)
}