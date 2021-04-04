package com.yokoro.terminal_lib.usecase.terminalbuffer

import core.UseCase
import core.UseCase.None
import com.yokoro.terminal_lib.entity.TerminalRow
import com.yokoro.terminal_lib.repository.ITerminalRepository

class GetTerminalBuffer constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<ArrayList<TerminalRow>, None>() {

    override suspend fun run(params: None) = ITerminalRepository.getTerminalBuffer()
}