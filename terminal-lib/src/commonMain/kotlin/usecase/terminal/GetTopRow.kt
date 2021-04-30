package usecase.terminal

import core.UseCase
import core.UseCase.None
import repository.ITerminalRepository

class GetTopRow constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Int, None>() {

    override fun run(params: None) = ITerminalRepository.getTopRow()
}