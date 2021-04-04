package usecase.cursor

import core.UseCase
import core.UseCase.None
import com.yokoro.terminal_lib.repository.ITerminalRepository

class IsDisplaying constructor(
    private val terminalRepository: ITerminalRepository
    ): UseCase<Boolean, None>() {

    override fun run(params: None) = terminalRepository.isDisplaying()

}