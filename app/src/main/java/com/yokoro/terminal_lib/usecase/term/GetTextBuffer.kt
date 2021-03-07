package com.yokoro.terminal_lib.usecase.term

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.TerminalRow
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class GetTextBuffer
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<ArrayList<TerminalRow>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, ArrayList<TerminalRow>>
        = ITerminalRepository.getTextBuffer()
}