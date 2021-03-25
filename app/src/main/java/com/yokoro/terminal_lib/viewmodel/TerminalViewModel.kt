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

    // TODO View の状態を書く
    var isShowingKeyboard: Boolean =  false

    var isUpdatingScreen = false        // 画面更新中はtrue

    var touchedY = 0

    suspend fun getCursor() = cursorUseCase.getCursor()

    suspend fun getTerminalBuffer() = terminalBufferUseCase.getTerminalBuffer()

    suspend fun getTopRow() = terminalUseCases.getTopRow()

    suspend fun getScreenSize() = terminalUseCases.getScreenSize()

    private fun isEscapeSequence(text: Char): Boolean {
        // TODO implement
        if (text != '\u001b') {
            return false
        }
        return true
    }

    fun inputText(text: Char) {
        if (isEscapeSequence(text)) {
            return
        }
        runBlocking {
            terminalBufferUseCase.setText(getCursor().x, getCursor().y + terminalUseCases.getTopRow(), text)
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