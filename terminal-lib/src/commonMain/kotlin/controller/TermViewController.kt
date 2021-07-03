package controller

import entity.ScreenSize
import usecase.UseCaseInputPort

class TerminalViewController: ITerminalViewController {
    lateinit var input: UseCaseInputPort

    fun getCursor() = input.getCursor()

    fun getScreenSize() = input.getScreenSize()

    override fun inputText(text: String) {
        input.writeCharOrRunEscSeq(text)
    }

    override fun scrollUp(n: Int) {
        input.scrollUp(n)
    }

    override fun scrollDown(n: Int) {
        input.scrollDown(n)
    }

    override fun changeScreenSize(screenSize: ScreenSize) {
        input.setScreenSize(screenSize)
    }

    override fun getTerminalBuffer() = input.getScreenText()
}