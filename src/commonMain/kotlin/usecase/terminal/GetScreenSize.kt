package usecase.terminal

import core.UseCase
import core.UseCase.None
import entity.ScreenSize
import com.yokoro.terminal_lib.repository.ITerminalRepository

class GetScreenSize constructor(
    private val ITerminalRepository: ITerminalRepository
): UseCase<ScreenSize, None>() {

    override fun run(params: None) = ITerminalRepository.getScreenSize()

}