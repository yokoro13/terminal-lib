package usecase.terminalbuffer

import core.UseCase
import core.UseCase.None
import entity.TerminalArray
import com.yokoro.terminal_lib.repository.ITerminalRepository

class GetTerminalBuffer constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<ArrayList<TerminalArray>, None>() {

    override fun run(params: None) = ITerminalRepository.getTerminalBuffer()
}