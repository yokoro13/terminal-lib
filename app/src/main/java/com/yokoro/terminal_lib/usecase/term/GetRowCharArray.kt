package com.yokoro.terminal_lib.usecase.term

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.repository.TerminalRepository
import javax.inject.Inject

class GetRowCharArray
@Inject constructor(
    private val terminalRepository: TerminalRepository
    ): UseCase<CharArray, GetRowCharArray.Params>() {

    override suspend fun run(params: Params): Either<Failure, CharArray>
        = terminalRepository.getRowCharArrayAt(params.y)

    data class Params(val y: Int)
}