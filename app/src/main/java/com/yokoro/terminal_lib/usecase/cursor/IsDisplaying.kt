package com.yokoro.terminal_lib.usecase.cursor

import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.core.UseCase.None
import com.yokoro.terminal_lib.repository.ITerminalRepository

class IsDisplaying constructor(
    private val terminalRepository: ITerminalRepository
    ): UseCase<Boolean, None>() {

    override suspend fun run(params: None) = terminalRepository.isDisplaying()

}