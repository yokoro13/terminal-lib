package com.yokoro.terminal_lib.usecase

import com.yokoro.terminal_lib.usecase.term.*

class EscapeSequenceUseCases (
    private val addNewRow: AddNewRow,
    private val getTextBuffer: GetTextBuffer,
    private val setColor: SetColor,
    private val setText: SetText,
    private val getScreenSize: GetScreenSize,
    private val getTopRow: GetTopRow,
    private val setTopRow: SetTopRow
) {
}