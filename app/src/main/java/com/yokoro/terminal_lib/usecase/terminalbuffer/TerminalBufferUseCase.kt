package com.yokoro.terminal_lib.usecase.terminalbuffer

import arrow.core.None
import arrow.core.getOrElse
import com.yokoro.terminal_lib.entity.TerminalRow
import com.yokoro.terminal_lib.usecase.terminalbuffer.SetColor
import com.yokoro.terminal_lib.usecase.terminalbuffer.SetText
import java.lang.IllegalArgumentException

class TerminalBufferUseCase(
    private val addNewRow: AddNewRow,
    private val getTerminalBuffer: GetTerminalBuffer,
    private val setText: SetText,
    private val setColor: SetColor
): ITerminalBufferUseCase {
    override suspend fun addNewRow(warp: Boolean) {
        addNewRow.run(AddNewRow.Params(warp))
    }

    override suspend fun getTerminalBuffer(): ArrayList<TerminalRow> =
        getTerminalBuffer.run(None).getOrElse { throw IllegalArgumentException("") }

    override suspend fun setText(x: Int, y: Int, text: Char) {
        setText.run(SetText.Params(x, y, text))
    }

    override suspend fun setColor(x: Int, y: Int, color: Int) {
        setColor.run(SetColor.Params(x, y, color))
    }
}