package usecase.terminalbuffer

import core.UseCase.None
import core.getOrElse
import entity.TerminalArray
import entity.TerminalChar

class TerminalBufferUseCase(
    private val addNewRow: AddNewRow,
    private val getTerminalBuffer: GetTerminalBuffer,
    private val setTerminalChar: SetTerminalChar,
    private val setChar: SetChar,
    private val setForeColor: SetForeColor,
    private val setBackColor: SetBackColor
): ITerminalBufferUseCase {
    override fun addNewRow(warp: Boolean) {
        addNewRow.run(AddNewRow.Params(warp))
    }

    override fun getTerminalBuffer(): ArrayList<TerminalArray> =
        getTerminalBuffer.run(None()).getOrElse { throw IllegalArgumentException("") }

    override fun setTerminalChar(x: Int, y: Int, terminalChar: TerminalChar) {
        setTerminalChar.run(SetTerminalChar.Params(x, y, terminalChar))
    }

    override fun setChar(x: Int, y: Int, char: Char) {
        setChar.run(SetChar.Params(x, y, char))
    }

    override fun setForeColor(x: Int, y: Int, color: Int) {
        setForeColor.run(SetForeColor.Params(x, y, color))
    }

    override fun setBackColor(x: Int, y: Int, color: Int) {
        setBackColor.run(SetBackColor.Params(x, y, color))
    }
}