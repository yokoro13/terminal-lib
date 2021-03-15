package com.yokoro.terminal_lib.usecase.terminal

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class SetTopRow
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
): UseCase<UseCase.None, SetTopRow.Params>() {

    override suspend fun run(params: Params): Either<Failure, UseCase.None>
            = ITerminalRepository.setTopRow(params.n)

    data class Params(val n: Int)
}