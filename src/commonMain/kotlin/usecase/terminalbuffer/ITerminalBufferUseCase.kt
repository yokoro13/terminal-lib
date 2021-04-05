package usecase.terminalbuffer

import entity.TerminalArray

/**
 * TerminalBuffer に関する UseCase を定義
 */
interface ITerminalBufferUseCase {

    fun addNewRow(warp: Boolean)

    fun getTerminalBuffer(): ArrayList<TerminalArray>

    fun setText(x: Int, y: Int, text: Char)

    fun setColor(x: Int, y: Int, color: Int)
}