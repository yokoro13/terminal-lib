package com.yokoro.terminal_lib.usecase.cursor

import arrow.core.Either
import arrow.core.None
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class SetDisplayingState @Inject constructor(
    private val terminalRepository: ITerminalRepository
): UseCase<None, SetDisplayingState.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> =
        terminalRepository.setDisplayingState(params.state)

    data class Params(val state: Boolean)
}