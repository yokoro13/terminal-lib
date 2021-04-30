package usecase.terminal

import core.UseCase
import core.UseCase.None
import entity.ScreenSize
import repository.ITerminalRepository

class GetScreenSize constructor(
    private val ITerminalRepository: ITerminalRepository
): UseCase<ScreenSize, None>() {

    override fun run(params: None) = ITerminalRepository.getScreenSize()

}