package usecase.terminal

import core.UseCase.None
import entity.Cursor
import entity.ScreenSize
import usecase.terminalbuffer.ITerminalBufferUseCase
import core.getOrElse

class TerminalUseCase (
    private val createTerminal: CreateTerminal,
    private val resize: Resize,
    private val getScreenSize: GetScreenSize,
    private val setTopRow: SetTopRow,
    private val getTopRow: GetTopRow,
    private val terminalBufferUseCase: ITerminalBufferUseCase
    ): ITerminalUseCase {

    override suspend fun createTerminal(screenSize: ScreenSize) {
        createTerminal.run(CreateTerminal.Params(screenSize))
    }

    override suspend fun resize(newScreenSize: ScreenSize) {
        resize.run(Resize.Params(newScreenSize))
    }

    override suspend fun getScreenSize(): ScreenSize =
        getScreenSize.run(None()).getOrElse { throw IllegalArgumentException("") }

    override suspend fun getTopRow(): Int =
        getTopRow.run(None()).getOrElse { throw IllegalArgumentException("") }

    override suspend fun setTopRow(n: Int) {
        setTopRow.run(SetTopRow.Params(n))
    }

    override suspend fun inputText(cursor: Cursor, text: Char) {
        terminalBufferUseCase.setText(cursor.x, cursor.y, text)
    }

    override suspend fun inputColor(cursor: Cursor, color: Int) {
        terminalBufferUseCase.setColor(cursor.x, cursor.y, color)
    }

}