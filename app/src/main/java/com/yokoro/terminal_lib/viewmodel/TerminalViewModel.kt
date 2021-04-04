package com.yokoro.terminal_lib.viewmodel

import androidx.lifecycle.ViewModel
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.usecase.cursor.ICursorUseCase
import com.yokoro.terminal_lib.usecase.escapesequence.IEscapeSequenceUseCase
import com.yokoro.terminal_lib.usecase.screen.IScreenUseCase
import com.yokoro.terminal_lib.usecase.terminal.ITerminalUseCase
import com.yokoro.terminal_lib.usecase.terminalbuffer.ITerminalBufferUseCase
import kotlinx.coroutines.runBlocking

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
    ): ViewModel() {

    var isShowingKeyboard: Boolean =  false

    var isUpdatingScreen = false        // 画面更新中はtrue

    var touchedY = 0

    private var escString: String = ""

    suspend fun getCursor() = cursorUseCase.getCursor()

    suspend fun getTerminalBuffer() = terminalBufferUseCase.getTerminalBuffer()

    suspend fun getTopRow() = terminalUseCases.getTopRow()

    suspend fun getScreenSize() = terminalUseCases.getScreenSize()


    fun runOperationCode(code: Char) {

    }

    fun writeCharAtCursor(text: Char) {
        runBlocking {
            terminalBufferUseCase.setText(getCursor().x, getCursor().y + terminalUseCases.getTopRow(), text)
        }
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

    private fun invalidEscapeSequence(){
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
                n = Integer.parseInt(sequence.substring(1, length - 1))
            } else {
                semicolonPos = sequence.indexOf(";")
                if (semicolonPos != 1) {
                    n = Integer.parseInt(sequence.substring(1, semicolonPos))
                }
                if (sequence[semicolonPos + 1] != 'H' || sequence[semicolonPos + 1] != 'f') {
                    m = Integer.parseInt(sequence.substring(semicolonPos + 1, length - 1))
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