package com.yokoro.terminal_lib.usecase.terminalbuffer

import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.Terminal
import com.yokoro.terminal_lib.repository.ITerminalRepository

class SetColor constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Terminal, SetColor.Params>() {

    data class Params(val x: Int, val y: Int, val color: Int)

    override suspend fun run(params: Params) = ITerminalRepository.setColor(params.x, params.y, params.color)
}