package usecase.terminalbuffer

import core.UseCase
import com.yokoro.terminal_lib.entity.Terminal
import com.yokoro.terminal_lib.repository.ITerminalRepository

class SetForeColor constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Terminal, SetForeColor.Params>() {

    data class Params(val x: Int, val y: Int, val color: Int)

    override fun run(params: Params) = ITerminalRepository.setForeColor(params.x, params.y, params.color)
}