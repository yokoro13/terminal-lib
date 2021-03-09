package com.yokoro.terminal_lib.usecase

import com.yokoro.terminal_lib.usecase.term.*

class TerminalUseCases (
    private val getTextBuffer: GetTextBuffer,
    private val resize: Resize,
    private val setColor: SetColor,
    private val setText: SetText,
    private val getScreenSize: GetScreenSize,
    ) {
}