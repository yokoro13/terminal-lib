package com.yokoro.terminal_lib.usecase.terminal

import arrow.core.Either
import arrow.core.None
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class SetCurrentRow
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
): UseCase<None, SetCurrentRow.Params>() {

    override suspend fun run(params: Params): Either<Failure, None>
            = ITerminalRepository.setCurrentRow(params.n)

    data class Params(val n: Int)
}