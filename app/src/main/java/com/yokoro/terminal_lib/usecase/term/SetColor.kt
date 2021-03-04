package com.yokoro.terminal_lib.usecase.term

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.TerminalBuffer
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class SetColor
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
): UseCase<TerminalBuffer, SetColor.Params>() {

    data class Params(val x: Int, val y: Int, val color: Int)

    override suspend fun run(params: Params): Either<Failure, TerminalBuffer> =
        ITerminalRepository.setColor(params.x, params.y, params.color)
}