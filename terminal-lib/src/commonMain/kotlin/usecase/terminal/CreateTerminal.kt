package usecase.terminal

import core.UseCase
import core.UseCase.None
import entity.ScreenSize
import repository.ITerminalRepository

class CreateTerminal constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<None, CreateTerminal.Params>() {

    override fun run(params: Params) = ITerminalRepository.createTerminal(params.screenSize)

    data class Params(val screenSize: ScreenSize)
}