package com.yokoro.terminal_lib.usecase.terminal

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.Terminal
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class AddNewRow
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Terminal, AddNewRow.Params>() {
    override suspend fun run(params: Params): Either<Failure, Terminal> =
        ITerminalRepository.addRow()

    data class Params(val warp: Boolean = false)
}