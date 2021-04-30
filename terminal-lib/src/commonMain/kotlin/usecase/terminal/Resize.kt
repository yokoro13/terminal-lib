package usecase.terminal

import core.UseCase
import entity.ScreenSize
import com.yokoro.terminal_lib.entity.Terminal
import repository.ITerminalRepository

class Resize constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Terminal, Resize.Params>() {

    override fun run(params: Params) = ITerminalRepository.resize(params.newScreenSize)

    data class Params(val newScreenSize: ScreenSize)
}