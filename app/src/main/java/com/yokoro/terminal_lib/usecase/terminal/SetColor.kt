package com.yokoro.terminal_lib.usecase.terminal

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.Terminal
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class SetColor
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
): UseCase<Terminal, SetColor.Params>() {

    data class Params(val x: Int, val y: Int, val color: Int)

    override suspend fun run(params: Params): Either<Failure, Terminal> =
        ITerminalRepository.setColor(params.x, params.y, params.color)
}