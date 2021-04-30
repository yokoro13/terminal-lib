package usecase.terminalbuffer

import core.UseCase
import core.UseCase.None
import entity.TerminalArray
import repository.ITerminalRepository

class GetTerminalBuffer constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<ArrayList<TerminalArray>, None>() {

    override fun run(params: None) = ITerminalRepository.getTerminalBuffer()
}