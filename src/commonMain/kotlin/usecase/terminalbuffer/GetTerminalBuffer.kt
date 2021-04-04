package usecase.terminalbuffer

import core.UseCase
import core.UseCase.None
import entity.TerminalRow
import com.yokoro.terminal_lib.repository.ITerminalRepository

class GetTerminalBuffer constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<ArrayList<TerminalRow>, None>() {

    override fun run(params: None) = ITerminalRepository.getTerminalBuffer()
}