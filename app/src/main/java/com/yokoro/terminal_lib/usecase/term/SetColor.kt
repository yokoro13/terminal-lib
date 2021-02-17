package com.yokoro.terminal_lib.usecase.term

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.TerminalBuffer
import com.yokoro.terminal_lib.repository.TerminalRepository
import javax.inject.Inject

class SetColor
@Inject constructor(
    private val terminalRepository: TerminalRepository
): UseCase<TerminalBuffer, SetColor.Params>() {

    data class Params(val x: Int, val y: Int, val color: Int)

    override suspend fun run(params: Params): Either<Failure, TerminalBuffer> =
        terminalRepository.setColor(params.x, params.y, params.color)
}