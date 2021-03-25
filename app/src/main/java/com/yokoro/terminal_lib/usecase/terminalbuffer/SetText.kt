package com.yokoro.terminal_lib.usecase.terminalbuffer

import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.Terminal
import com.yokoro.terminal_lib.repository.ITerminalRepository
import javax.inject.Inject

class SetText
@Inject constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Terminal, SetText.Params>() {

    override suspend fun run(params: Params) = ITerminalRepository.setText(params.x, params.y, params.text)

    data class Params(val x: Int, val y: Int, val text: Char)
}