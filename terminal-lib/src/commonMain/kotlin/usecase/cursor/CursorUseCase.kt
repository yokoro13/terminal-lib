package usecase.cursor

import core.UseCase.None
import core.getOrElse
import entity.Cursor
import entity.ScreenSize

class CursorUseCase (
    private val setCursor: SetCursor,
    private val getCursor: GetCursor,
    private val setDisplayingState: SetDisplayingState,
    private val isDisplaying: IsDisplaying
    ): ICursorUseCase {

    override fun setCursor(x: Int, y: Int) {
        setCursor.run(SetCursor.Params(x, y))
    }

    override fun moveUp(cursor: Cursor, ss: ScreenSize, n: Int) {
        setCursor.run(SetCursor.Params(getCursor().x, getCursor().y - n))
    }

    override fun moveDown(cursor: Cursor, ss: ScreenSize, n: Int) {
        setCursor.run(SetCursor.Params(getCursor().x, getCursor().y + n))
    }

    override fun moveRight(cursor: Cursor, ss: ScreenSize, n: Int) {
        setCursor.run(SetCursor.Params(getCursor().x + n, getCursor().y))
    }

    override fun moveLeft(cursor: Cursor, ss: ScreenSize, n: Int) {
        setCursor.run(SetCursor.Params(getCursor().x - n, getCursor().y))
    }

    override fun setDisplayingState(state: Boolean) {
        setDisplayingState.run(SetDisplayingState.Params(state))
    }

    override fun isDisplaying(): Boolean =
        isDisplaying.run(None()).getOrElse { throw IllegalArgumentException("") }

    override fun getCursor(): Cursor =
        getCursor.run(None()).getOrElse { throw IllegalArgumentException("") }
}