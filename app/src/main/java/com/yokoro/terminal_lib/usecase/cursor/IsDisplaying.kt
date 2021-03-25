package com.yokoro.terminal_lib.usecase.cursor

import arrow.core.None
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class IsDisplaying
@Inject constructor(
    private val terminalRepository: ITerminalRepository
    ): UseCase<Boolean, None>() {

    override suspend fun run(params: None) = terminalRepository.isDisplaying()

}