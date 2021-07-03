package presenter

import entity.Cursor
import entity.ScreenSize
import entity.TerminalArray
import entity.TerminalChar
import usecase.UseCaseOutputPort

class TerminalPresenter: UseCaseOutputPort {
    override fun getText(terminalChar: TerminalChar): TerminalChar = terminalChar

    override fun getScreenText(screenText: ArrayList<TerminalArray>): ArrayList<TerminalArray> = screenText

    override fun getCursor(cursor: Cursor): Cursor = cursor

    override fun getScreenSize(screenSize: ScreenSize): ScreenSize = screenSize
}