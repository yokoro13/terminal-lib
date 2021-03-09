package com.yokoro.terminal_lib.usecase

import com.yokoro.terminal_lib.usecase.cursor.*

class CursorUseCases (
    private val moveDown: MoveDown,
    private val moveLeft: MoveLeft,
    private val moveRight: MoveRight,
    private val moveUp: MoveUp,
    private val setCursor: SetCursor
    ) {
}