package com.yokoro.terminal_lib.usecase.terminal

import arrow.core.None
import core.UseCase
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class GetScreenSize
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
): UseCase<ScreenSize, None>() {

    override suspend fun run(params: None) = ITerminalRepository.getScreenSize()

}