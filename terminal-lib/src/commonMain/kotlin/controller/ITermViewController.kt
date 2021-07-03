package controller

import entity.ScreenSize
import entity.TerminalArray

interface ITerminalViewController {
    fun inputText(text: String)
    fun scrollUp(n: Int = 1)
    fun scrollDown(n: Int = 1)
    fun changeScreenSize(screenSize: ScreenSize)
    fun getTerminalBuffer(): ArrayList<TerminalArray>
}