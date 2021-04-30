package usecase.cursor

import core.UseCase
import core.UseCase.None
import repository.ITerminalRepository

class SetDisplayingState constructor(
    private val terminalRepository: ITerminalRepository
    ): UseCase<None, SetDisplayingState.Params>() {

    override fun run(params: Params) = terminalRepository.setDisplayingState(params.state)

    data class Params(val state: Boolean)
}