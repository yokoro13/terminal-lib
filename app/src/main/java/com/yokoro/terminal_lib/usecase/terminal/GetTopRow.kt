package com.yokoro.terminal_lib.usecase.terminal

import arrow.core.None
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class GetTopRow
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Int, None>() {

    override suspend fun run(params: None) = ITerminalRepository.getTopRow()
}