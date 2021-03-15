package com.yokoro.terminal_lib.usecase.terminal

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.entity.Terminal
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class Resize
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Terminal, Resize.Params>() {

    override suspend fun run(params: Params): Either<Failure, Terminal> =
        ITerminalRepository.resize(params.newScreenSize)

    data class Params(val newScreenSize: ScreenSize)
}