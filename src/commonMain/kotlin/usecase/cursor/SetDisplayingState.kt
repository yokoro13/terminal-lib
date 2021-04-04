package com.yokoro.terminal_lib.usecase.cursor

import core.UseCase
import core.UseCase.None
import com.yokoro.terminal_lib.repository.ITerminalRepository

class SetDisplayingState constructor(
    private val terminalRepository: ITerminalRepository
    ): UseCase<None, SetDisplayingState.Params>() {

    override suspend fun run(params: Params) = terminalRepository.setDisplayingState(params.state)

    data class Params(val state: Boolean)
}