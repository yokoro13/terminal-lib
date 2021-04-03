package com.yokoro.terminal_lib.usecase.cursor

import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.core.UseCase.None
import com.yokoro.terminal_lib.repository.ITerminalRepository

class SetCursor constructor(
    private val terminalRepository: ITerminalRepository
    ): UseCase<None, SetCursor.Params>() {

    override suspend fun run(params: Params) = terminalRepository.setCursor(params.x, params.y)

    data class Params(val x: Int, val y: Int)
}