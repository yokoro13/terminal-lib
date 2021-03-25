package com.yokoro.terminal_lib.usecase.screen

import com.yokoro.terminal_lib.usecase.cursor.ICursorUseCase
import com.yokoro.terminal_lib.usecase.terminal.ITerminalUseCase
import com.yokoro.terminal_lib.usecase.terminalbuffer.ITerminalBufferUseCase

class ScreenUseCase(
    private val terminalBufferUseCase: ITerminalBufferUseCase,
    private val terminalUseCase: ITerminalUseCase,
    private val cursorUseCase: ICursorUseCase
    ): IScreenUseCase {

    private suspend fun getBufferSize() = terminalBufferUseCase.getTerminalBuffer().size

    private suspend fun getScreenSize() = terminalUseCase.getScreenSize()

    private suspend fun getCursor() = cursorUseCase.getCursor()

    override suspend fun scrollDown() {
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

    override suspend fun scrollUp() {
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