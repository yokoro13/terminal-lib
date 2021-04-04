package com.yokoro.terminal_lib.usecase.escapesequence

import entity.Cursor

interface IEscapeSequenceUseCase {
    suspend fun moveRight(cursor: Cursor, n: Int)
    suspend fun moveLeft(cursor: Cursor, n: Int)
    suspend fun moveUp(cursor: Cursor, n: Int)
    suspend fun moveDown(cursor: Cursor, n: Int)
    suspend fun moveUpToLineHead(cursor: Cursor, n: Int)
    suspend fun moveDownToLineHead(cursor: Cursor, n: Int)
    suspend fun moveCursor(cursor: Cursor, n: Int)
    suspend fun moveCursor(cursor: Cursor, n: Int, m: Int)
    suspend fun clearScreen(cursor: Cursor, n: Int)
    suspend fun clearLine(cursor: Cursor, n: Int)
    suspend fun scrollNext(n: Int)
    suspend fun scrollBack(n: Int)
    suspend fun selectGraphicRendition(n: Int)
}