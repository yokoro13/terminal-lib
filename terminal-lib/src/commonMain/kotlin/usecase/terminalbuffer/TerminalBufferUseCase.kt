package usecase.terminalbuffer

import core.UseCase.None
import core.getOrElse
import entity.TerminalArray

class TerminalBufferUseCase(
    private val addNewRow: AddNewRow,
    private val getTerminalBuffer: GetTerminalBuffer,
    private val setText: SetText,
    private val setForeColor: SetForeColor,
    private val setBackColor: SetBackColor
): ITerminalBufferUseCase {
    override fun addNewRow(warp: Boolean) {
        addNewRow.run(AddNewRow.Params(warp))
    }

    override fun getTerminalBuffer(): ArrayList<TerminalArray> =
        getTerminalBuffer.run(None()).getOrElse { throw IllegalArgumentException("") }

    override fun setText(x: Int, y: Int, text: Char) {
        setText.run(SetText.Params(x, y, text))
    }

    override fun setForeColor(x: Int, y: Int, color: Int) {
        setForeColor.run(SetForeColor.Params(x, y, color))
    }

    override fun setBackColor(x: Int, y: Int, color: Int) {
        setBackColor.run(SetBackColor.Params(x, y, color))
    }
}