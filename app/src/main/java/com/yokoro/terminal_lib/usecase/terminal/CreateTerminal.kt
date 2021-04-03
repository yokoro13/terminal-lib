package com.yokoro.terminal_lib.usecase.terminal

import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.core.UseCase.None
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.repository.ITerminalRepository

class CreateTerminal constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<None, CreateTerminal.Params>() {

    override suspend fun run(params: Params) = ITerminalRepository.createTerminal(params.screenSize)

    data class Params(val screenSize: ScreenSize)
}