package usecase.terminalbuffer

import com.yokoro.terminal_lib.entity.Terminal
import com.yokoro.terminal_lib.repository.ITerminalRepository
import core.UseCase

class SetBackColor constructor(
    private val ITerminalRepository: ITerminalRepository
): UseCase<Terminal, SetBackColor.Params>() {

    data class Params(val x: Int, val y: Int, val color: Int)

    override fun run(params: Params) = ITerminalRepository.setBackColor(params.x, params.y, params.color)
}