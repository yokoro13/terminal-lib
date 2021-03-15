package com.yokoro.terminal_lib.viewmodel

import androidx.lifecycle.ViewModel
import com.yokoro.terminal_lib.usecase.escapesequence.IEscapeSequenceUseCase
import com.yokoro.terminal_lib.usecase.terminal.ITerminalUseCase

class TerminalViewModel (
    val escapeSequenceUseCase: IEscapeSequenceUseCase,
    val terminalUseCases: ITerminalUseCase
    ): ViewModel() {

    // TODO View の状態を書く

}