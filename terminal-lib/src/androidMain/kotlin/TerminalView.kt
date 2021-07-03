import controller.ITerminalViewController
import controller.TerminalViewController

class TerminalView {
    private val terminalViewController: ITerminalViewController = TerminalViewController()

    fun input(text: String){}

    fun scroll(){

    }

    fun moveCursorUp(){

    }

    fun moveCursorDown(){}

    fun moveCursorRight(){}

    fun moveCursorLeft(){}

    fun getScreenSize(){}

    fun getCursor(){}

    fun update() {
        val terminalRender: TerminalRender = TerminalRender()
        terminalRender.renderText(terminalViewController.getTerminalBuffer())
    }
}