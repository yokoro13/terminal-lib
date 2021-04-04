package usecase.cursor

import core.UseCase
import core.UseCase.None
import com.yokoro.terminal_lib.repository.ITerminalRepository

class SetCursor constructor(
    private val terminalRepository: ITerminalRepository
    ): UseCase<None, SetCursor.Params>() {

    override fun run(params: Params) = terminalRepository.setCursor(params.x, params.y)

    data class Params(val x: Int, val y: Int)
}