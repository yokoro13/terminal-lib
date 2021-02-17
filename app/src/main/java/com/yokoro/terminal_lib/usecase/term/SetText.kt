package com.yokoro.terminal_lib.usecase.term

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.TerminalBuffer
import com.yokoro.terminal_lib.repository.TerminalRepository
import javax.inject.Inject

class SetText
@Inject constructor(private val terminalRepository: TerminalRepository):
    UseCase<TerminalBuffer, SetText.Params>() {

    override suspend fun run(params: Params): Either<Failure, TerminalBuffer> =
        terminalRepository.setText(params.x, params.y, params.text)

    data class Params(val x: Int, val y: Int, val text: Char)
}