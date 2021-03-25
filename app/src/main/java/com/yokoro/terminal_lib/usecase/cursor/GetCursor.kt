package com.yokoro.terminal_lib.usecase.cursor

import arrow.core.None
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class GetCursor
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Cursor, None>() {

    override suspend fun run(params: None) = ITerminalRepository.getCursor()

}