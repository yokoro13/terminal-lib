package usecase.terminal

import core.UseCase
import core.UseCase.None
import repository.ITerminalRepository

class SetTopRow constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<None, SetTopRow.Params>() {

    override fun run(params: Params) = ITerminalRepository.setTopRow(params.n)

    data class Params(val n: Int)
}