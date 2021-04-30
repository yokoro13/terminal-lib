package usecase.cursor

import core.UseCase.None
import core.UseCase
import entity.Cursor
import repository.ITerminalRepository

class GetCursor constructor(
    private val ITerminalRepository: ITerminalRepository
    ): UseCase<Cursor, None>() {

    override fun run(params: None) = ITerminalRepository.getCursor()

}