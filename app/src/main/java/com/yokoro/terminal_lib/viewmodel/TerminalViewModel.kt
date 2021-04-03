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

    fun writeToBufferAtCursor(text: Char) {
        runBlocking {
            terminalBufferUseCase.setText(getCursor().x, getCursor().y + terminalUseCases.getTopRow(), text)
        }
    }

    fun runEscapeSequence(text: String) {
        text.forEach { checkEscapeSequenceCharAndRun(it) }
    }

    fun checkEscapeSequenceCharAndRun(text: Char) {
        escString += text
        if (!isEscapeSequence(escString)) {
            invalidEscapeSequence()
            return
        }

        if (escString.matches("$[A-HJKSTfm]".toRegex())) {
            escapeSequence(escString)
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
        escString.forEach { writeToBufferAtCursor(it) }
        escString = ""
    }

    private fun escapeSequence(sequence: String) {
        when (sequence.last()) {
            'A' -> {

            }
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