package usecase.terminalbuffer

import entity.TerminalArray

/**
 * TerminalBuffer に関する UseCase を定義
 */
interface ITerminalBufferUseCase {

    fun addNewRow(warp: Boolean)

    fun getTerminalBuffer(): ArrayList<TerminalArray>

    fun setText(x: Int, y: Int, text: Char)

    fun setForeColor(x: Int, y: Int, color: Int)

    fun setBackColor(x: Int, y: Int, color: Int)
}