package com.yokoro.terminal_lib.usecase.escapesequence

import com.yokoro.terminal_lib.entity.Cursor
import com.yokoro.terminal_lib.repository.ICursorRepository
import com.yokoro.terminal_lib.repository.ITerminalRepository

class EscapeSequenceInteractor(
    private var cursorRepository: ICursorRepository,
    private var terminalRepository: ITerminalRepository
): EscapeSequenceUseCase {
    override fun moveRight(cursor: Cursor, n: Int) {
        TODO("Not yet implemented")
    }

    override fun moveLeft(cursor: Cursor, n: Int) {
        TODO("Not yet implemented")
    }

    override fun moveUp(cursor: Cursor, n: Int) {
        TODO("Not yet implemented")
    }

    override fun moveDown(cursor: Cursor, n: Int) {
        TODO("Not yet implemented")
    }

    override fun moveUpToRowLead(cursor: Cursor, n: Int) {
        TODO("Not yet implemented")
    }

    override fun moveDownToRowLead(cursor: Cursor, n: Int) {
        TODO("Not yet implemented")
    }

    override fun moveCursor(cursor: Cursor, n: Int) {
        TODO("Not yet implemented")
    }

    override fun moveCursor(cursor: Cursor, n: Int, m: Int) {
        TODO("Not yet implemented")
    }

    override fun clearDisplay(cursor: Cursor, n: Int) {
        TODO("Not yet implemented")
    }

    override fun clearRow(cursor: Cursor, n: Int) {
        TODO("Not yet implemented")
    }

    override fun scrollNext(n: Int) {
        TODO("Not yet implemented")
    }

    override fun scrollBack(n: Int) {
        TODO("Not yet implemented")
    }

    override fun selectGraphicRendition(n: Int) {
        TODO("Not yet implemented")
    }

}