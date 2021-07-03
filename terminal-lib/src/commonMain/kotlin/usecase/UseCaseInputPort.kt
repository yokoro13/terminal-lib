package usecase

import entity.Cursor
import entity.ScreenSize
import entity.TerminalArray
import entity.TerminalChar

/**
 * FIXME 命名が不適切
 */

interface UseCaseInputPort {
    fun setTerminalChar(x: Int, y: Int, terminalChar: TerminalChar)
    fun setText(x: Int, y: Int, text: Char)
    fun setCursor(cursor: Cursor)
    fun setScreenSize(screenSize: ScreenSize)
    fun setForeColor(x: Int, y: Int, foreColor: Int)
    fun setBackColor(x: Int, y: Int, backColor: Int)

    fun getText(x: Int, y: Int): TerminalChar
    fun getScreenText(): ArrayList<TerminalArray>
    fun getCursor(): Cursor
    fun getScreenSize(): ScreenSize

    fun scrollDown(n: Int)
    fun scrollUp(n: Int)

    fun writeCharOrRunEscSeq(text: String)
}