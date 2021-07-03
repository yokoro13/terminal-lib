package usecase.terminalbuffer

import com.yokoro.terminal_lib.entity.Terminal
import core.UseCase
import entity.TerminalChar
import repository.ITerminalRepository

class SetTerminalChar constructor(
    private val ITerminalRepository: ITerminalRepository
): UseCase<Terminal, SetTerminalChar.Params>() {

    override fun run(params: Params) = ITerminalRepository.setTerminalChar(params.x, params.y, params.terminalChar)

    data class Params(val x: Int, val y: Int, val terminalChar: TerminalChar)
}