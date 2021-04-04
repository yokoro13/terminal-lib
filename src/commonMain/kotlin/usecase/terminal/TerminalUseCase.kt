package com.yokoro.terminal_lib.usecase.terminal

import core.UseCase.None
import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.usecase.terminalbuffer.ITerminalBufferUseCase

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