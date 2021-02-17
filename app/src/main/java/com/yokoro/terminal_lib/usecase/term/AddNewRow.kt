package com.yokoro.terminal_lib.usecase.term

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.TerminalBuffer
import com.yokoro.terminal_lib.repository.TerminalRepository
import javax.inject.Inject

class AddNewRow
@Inject constructor(
    private val terminalRepository: TerminalRepository
    ): UseCase<TerminalBuffer, AddNewRow.Params>() {
    override suspend fun run(params: Params): Either<Failure, TerminalBuffer> =
        terminalRepository.addRow()

    data class Params(val warp: Boolean = false)
}