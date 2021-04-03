package com.yokoro.terminal_lib.usecase.cursor

import com.yokoro.terminal_lib.core.UseCase.None
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.repository.ITerminalRepository

class GetCursor constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Cursor, None>() {

    override suspend fun run(params: None) = ITerminalRepository.getCursor()

}