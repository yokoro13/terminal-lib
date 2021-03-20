package com.yokoro.terminal_lib.usecase.cursor

import arrow.core.Either
import arrow.core.None
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class SetCursor
@Inject constructor(
    private val terminalRepository: ITerminalRepository
): UseCase<None, SetCursor.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> =
        terminalRepository.setCursor(params.x, params.y)

    data class Params(val x: Int, val y: Int)
}