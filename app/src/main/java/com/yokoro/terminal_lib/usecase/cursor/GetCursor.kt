package com.yokoro.terminal_lib.usecase.cursor

import arrow.core.Either
import arrow.core.None
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class GetCursor
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
): UseCase<Cursor, None>() {

    override suspend fun run(params: None): Either<Failure, Cursor>
            = ITerminalRepository.getCursor()

}