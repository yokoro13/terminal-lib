package usecase.terminalbuffer

import core.UseCase
import com.yokoro.terminal_lib.entity.Terminal
import repository.ITerminalRepository

class SetChar constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Terminal, SetChar.Params>() {

    override fun run(params: Params) = ITerminalRepository.setChar(params.x, params.y, params.text)

    data class Params(val x: Int, val y: Int, val text: Char)
}