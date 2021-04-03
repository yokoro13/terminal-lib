package com.yokoro.terminal_lib.usecase.terminal

import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.core.UseCase.None
import com.yokoro.terminal_lib.repository.ITerminalRepository

class GetTopRow constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Int, None>() {

    override suspend fun run(params: None) = ITerminalRepository.getTopRow()
}