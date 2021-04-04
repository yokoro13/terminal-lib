package usecase.escapesequence

import entity.Cursor

interface IEscapeSequenceUseCase {
    fun moveRight(cursor: Cursor, n: Int)
    fun moveLeft(cursor: Cursor, n: Int)
    fun moveUp(cursor: Cursor, n: Int)
    fun moveDown(cursor: Cursor, n: Int)
    fun moveUpToLineHead(cursor: Cursor, n: Int)
    fun moveDownToLineHead(cursor: Cursor, n: Int)
    fun moveCursor(cursor: Cursor, n: Int)
    fun moveCursor(cursor: Cursor, n: Int, m: Int)
    fun clearScreen(cursor: Cursor, n: Int)
    fun clearLine(cursor: Cursor, n: Int)
    fun scrollNext(n: Int)
    fun scrollBack(n: Int)
    fun selectGraphicRendition(n: Int)
}