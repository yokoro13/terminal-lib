package usecase.cursor

import core.UseCase.None
import core.UseCase
import entity.Cursor
import com.yokoro.terminal_lib.repository.ITerminalRepository

class GetCursor constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Cursor, None>() {

    override fun run(params: None) = ITerminalRepository.getCursor()

}