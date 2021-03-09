package com.yokoro.terminal_lib.viewmodel

import androidx.lifecycle.ViewModel
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.usecase.EscapeSequenceUseCases
import com.yokoro.terminal_lib.usecase.TerminalUseCases
import com.yokoro.terminal_lib.usecase.cursor.*
import com.yokoro.terminal_lib.usecase.term.*

class TerminalViewModel (
    val escapeSequenceUseCases: EscapeSequenceUseCases,
    val terminalUseCases: TerminalUseCases
    ): ViewModel() {

    // TODO View の状態を書く

}