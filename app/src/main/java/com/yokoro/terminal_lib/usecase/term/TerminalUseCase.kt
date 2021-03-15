package com.yokoro.terminal_lib.usecase.term

import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.usecase.term.*

class TerminalUseCase (
    private val addNewRow: AddNewRow,
    private val createTerminalBuffer: CreateTerminalBuffer,
    private val resize: Resize,
    private val setColor: SetColor,
    private val setText: SetText,
    private val getScreenSize: GetScreenSize,
    ): ITerminalUseCase {

    override suspend fun addNewRow(warp: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun createTerminalBuffer() {
        TODO("Not yet implemented")
    }

    override suspend fun resize() {
        TODO("Not yet implemented")
    }

    override suspend fun getScreenSize(): ScreenSize {
        TODO("Not yet implemented")
    }

    override suspend fun getTopRow(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun setTopRow(n: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun inputText(cursor: Cursor, text: Char) {
        TODO("Not yet implemented")
    }

    override suspend fun inputColor(cursor: Cursor, color: Int) {
        TODO("Not yet implemented")
    }
}