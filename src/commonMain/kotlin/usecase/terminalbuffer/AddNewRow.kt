package usecase.terminalbuffer

import core.UseCase
import com.yokoro.terminal_lib.entity.Terminal
import com.yokoro.terminal_lib.repository.ITerminalRepository

class AddNewRow constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Terminal, AddNewRow.Params>() {

    override fun run(params: Params) = ITerminalRepository.addRow()

    data class Params(val warp: Boolean = false)
}