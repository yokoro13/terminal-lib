package usecase.screen

import usecase.cursor.ICursorUseCase
import usecase.terminal.ITerminalUseCase
import usecase.terminalbuffer.ITerminalBufferUseCase

class ScreenUseCase(
    private val terminalBufferUseCase: ITerminalBufferUseCase,
    private val terminalUseCase: ITerminalUseCase,
    private val cursorUseCase: ICursorUseCase
    ): IScreenUseCase {

    private fun getBufferSize() = terminalBufferUseCase.getTerminalBuffer().size

    private fun getScreenSize() = terminalUseCase.getScreenSize()

    private fun getCursor() = cursorUseCase.getCursor()

    override fun scrollDown() {
        if (terminalUseCase.getTopRow() + terminalUseCase.getScreenSize().rows < getBufferSize()) {
            //表示する一番上の行を１つ下に
            terminalUseCase.setTopRow(terminalUseCase.getTopRow()+1)
            if (getCursor().y + 1 != getScreenSize().rows) {
                cursorUseCase.setCursor(getCursor().x, getCursor().y++)
            } else {
                cursorUseCase.setDisplayingState(false)
            }
        }
    }

    override fun scrollUp() {
        if (getScreenSize().rows < getBufferSize()) {
            //表示する一番上の行を１つ上に
            terminalUseCase.setTopRow(terminalUseCase.getTopRow()-1)
            // カーソルが画面内にある
            if (getCursor().y != 0) {
                cursorUseCase.setCursor(getCursor().x, getCursor().y--)
            } else {
                cursorUseCase.setDisplayingState(false)
            }
        }
    }
}