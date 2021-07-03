package usecase

import entity.Cursor
import entity.ScreenSize
import entity.TerminalArray
import entity.TerminalChar

/**
 * FIXME 命名が不適切
 */

interface UseCaseOutputPort {
    fun getText(terminalChar: TerminalChar): TerminalChar
    fun getScreenText(screenText: ArrayList<TerminalArray>): ArrayList<TerminalArray>
    fun getCursor(cursor: Cursor): Cursor
    fun getScreenSize(screenSize: ScreenSize): ScreenSize
}