package usecase.terminalbuffer

import entity.TerminalArray
import entity.TerminalChar

/**
 * TerminalBuffer に関する UseCase を定義
 */
interface ITerminalBufferUseCase {

    fun addNewRow(warp: Boolean)

    fun getTerminalBuffer(): ArrayList<TerminalArray>

    fun setTerminalChar(x: Int, y: Int, terminalChar: TerminalChar)

    fun setChar(x: Int, y: Int, char: Char)

    fun setForeColor(x: Int, y: Int, color: Int)

    fun setBackColor(x: Int, y: Int, color: Int)
}