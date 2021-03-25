package com.yokoro.terminal_lib.usecase.terminal

import arrow.core.None
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class SetTopRow
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<None, SetTopRow.Params>() {

    override suspend fun run(params: Params) = ITerminalRepository.setTopRow(params.n)

    data class Params(val n: Int)
}