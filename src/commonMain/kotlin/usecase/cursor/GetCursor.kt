package com.yokoro.terminal_lib.usecase.cursor

import core.UseCase.None
import core.UseCase
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.repository.ITerminalRepository

class GetCursor constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Cursor, None>() {

    override suspend fun run(params: None) = ITerminalRepository.getCursor()

}