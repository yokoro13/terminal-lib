package usecase.cursor

import entity.Cursor
import entity.ScreenSize

interface ICursorUseCase {
    fun setCursor(x: Int, y: Int)
    fun getCursor(): Cursor
    fun moveUp(cursor: Cursor, ss: ScreenSize, n: Int)
    fun moveDown(cursor: Cursor, ss: ScreenSize, n: Int)
    fun moveRight(cursor: Cursor, ss: ScreenSize, n: Int)
    fun moveLeft(cursor: Cursor, ss: ScreenSize, n: Int)
    fun setDisplayingState(state: Boolean)
    fun isDisplaying(): Boolean
}