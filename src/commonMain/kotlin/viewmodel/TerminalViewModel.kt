package viewmodel

import entity.ScreenSize
import usecase.cursor.ICursorUseCase
import com.yokoro.terminal_lib.usecase.escapesequence.IEscapeSequenceUseCase
import usecase.screen.IScreenUseCase
import usecase.terminal.ITerminalUseCase
import usecase.terminalbuffer.ITerminalBufferUseCase

/**
 * プラットフォーム問わず必要な処理を記述
 *
 */

class TerminalViewModel (
    private val escapeSequenceUseCase: IEscapeSequenceUseCase,
    private val terminalUseCases: ITerminalUseCase,
    private val terminalBufferUseCase: ITerminalBufferUseCase,
    private val screenUseCase: IScreenUseCase,
    private val cursorUseCase: ICursorUseCase
    ) {

    var isShowingKeyboard: Boolean =  false

    var isUpdatingScreen = false        // 画面更新中はtrue

    var touchedY = 0

    private var escString: String = ""

    suspend fun getCursor() = cursorUseCase.getCursor()

    suspend fun getTerminalBuffer() = terminalBufferUseCase.getTerminalBuffer()

    suspend fun getTopRow() = terminalUseCases.getTopRow()

    suspend fun getScreenSize() = terminalUseCases.getScreenSize()

    suspend fun runOperationCode(code: Char) {
        when (code) {
            '\r' -> cursorUseCase.setCursor(0, getCursor().y)
            '\n' -> {

            }
            '\b' -> {}
            '\t' -> {}
        }
    }

    suspend fun writeCharAtCursor(text: Char) {
        terminalBufferUseCase.setText(getCursor().x, getCursor().y + terminalUseCases.getTopRow(), text)
    }

    suspend fun runEscapeSequence(text: String) {
        text.forEach { checkEscapeSequenceCharAndRun(it) }
    }

    suspend fun checkEscapeSequenceCharAndRun(text: Char) {
        escString += text
        if (!isEscapeSequence(escString)) {
            invalidEscapeSequence()
            return
        }

        if (escString.matches("$[A-HJKSTfm]".toRegex())) {
            escapeSequence(escString)
            escString = ""
        }
    }

    private fun isEscapeSequence(sequence: String): Boolean {
        return sequence.matches("(^\\u001b)".toRegex())
                || sequence.matches("(^\\u001b\\[)".toRegex())
                || sequence.matches("(^\\u001b\\[)(\\d*)".toRegex())
                || sequence.matches("(^\\u001b\\[)(\\d*);".toRegex())
                || sequence.matches("(^\\u001b\\[)(\\d*);(\\d*)".toRegex())
                || sequence.matches("(^\\u001b\\[)(\\d*)([A-GJKSTm])".toRegex())
                || sequence.matches("(^\\u001b\\[)(\\d*);(\\d*)([Hf])".toRegex())
    }

    private suspend fun invalidEscapeSequence(){
        escString.forEach { writeCharAtCursor(it) }
        escString = ""
    }

    private suspend fun escapeSequence(sequence: String) {
        val length = sequence.length
        val mode = sequence[length - 1]
        var n = 1
        var m = 1
        val semicolonPos: Int

        if (length != 2) {
            if (mode != 'H') {
                n = sequence.substring(1, length - 1).toInt()
            } else {
                semicolonPos = sequence.indexOf(";")
                if (semicolonPos != 1) {
                    n = sequence.substring(1, semicolonPos).toInt()
                }
                if (sequence[semicolonPos + 1] != 'H' || sequence[semicolonPos + 1] != 'f') {
                    m = sequence.substring(semicolonPos + 1, length - 1).toInt()
                }
            }
        }

        when (mode) {
            'A' -> escapeSequenceUseCase.moveUp(getCursor(), n)
            'B' -> escapeSequenceUseCase.moveDown(getCursor(), n)
            'C' -> escapeSequenceUseCase.moveRight(getCursor(), n)
            'D' -> escapeSequenceUseCase.moveLeft(getCursor(), n)
            'E' -> escapeSequenceUseCase.moveDownToLineHead(getCursor(), n)
            'F' -> escapeSequenceUseCase.moveUpToLineHead(getCursor(), n)
            'G' -> escapeSequenceUseCase.moveCursor(getCursor(), n)
            'H', 'f' -> escapeSequenceUseCase.moveCursor(getCursor(), n, m)
            'J' -> escapeSequenceUseCase.clearScreen(getCursor(), n-1)
            'K' -> escapeSequenceUseCase.clearLine(getCursor(), n-1)
            'S' -> escapeSequenceUseCase.scrollNext(n)
            'T' -> escapeSequenceUseCase.scrollBack(n)
            'm' -> escapeSequenceUseCase.selectGraphicRendition(n)
        }
    }

    suspend fun changeScreenSize(screenSize: ScreenSize) {
        terminalUseCases.resize(screenSize)
    }

    suspend fun scrollDown() {
        screenUseCase.scrollDown()
    }

    suspend fun scrollUp() {
        screenUseCase.scrollUp()
    }

}