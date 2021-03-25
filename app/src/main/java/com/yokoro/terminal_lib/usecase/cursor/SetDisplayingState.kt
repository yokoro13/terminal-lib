package com.yokoro.terminal_lib.usecase.cursor

import arrow.core.None
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class SetDisplayingState @Inject constructor(
    private val terminalRepository: ITerminalRepository
    ): UseCase<None, SetDisplayingState.Params>() {

    override suspend fun run(params: Params) = terminalRepository.setDisplayingState(params.state)

    data class Params(val state: Boolean)
}