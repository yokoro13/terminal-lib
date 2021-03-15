package com.yokoro.terminal_lib.usecase.terminal

import arrow.core.Either
import arrow.core.None
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class CreateTerminal
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
): UseCase<None, CreateTerminal.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> =
        ITerminalRepository.createTerminal(params.screenSize)

    data class Params(val screenSize: ScreenSize)
}