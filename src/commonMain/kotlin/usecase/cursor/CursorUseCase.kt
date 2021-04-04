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

    override suspend fun setCursor(x: Int, y: Int) {
        setCursor.run(SetCursor.Params(x, y))
    }

    override suspend fun moveUp(cursor: Cursor, ss: ScreenSize, n: Int) {
        setCursor.run(SetCursor.Params(getCursor().x, getCursor().y - n))
    }

    override suspend fun moveDown(cursor: Cursor, ss: ScreenSize, n: Int) {
        setCursor.run(SetCursor.Params(getCursor().x, getCursor().y + n))
    }

    override suspend fun moveRight(cursor: Cursor, ss: ScreenSize, n: Int) {
        setCursor.run(SetCursor.Params(getCursor().x + n, getCursor().y))
    }

    override suspend fun moveLeft(cursor: Cursor, ss: ScreenSize, n: Int) {
        setCursor.run(SetCursor.Params(getCursor().x - n, getCursor().y))
    }

    override suspend fun setDisplayingState(state: Boolean) {
        setDisplayingState.run(SetDisplayingState.Params(state))
    }

    override suspend fun isDisplaying(): Boolean =
        isDisplaying.run(None()).getOrElse { throw IllegalArgumentException("") }

    override suspend fun getCursor(): Cursor =
        getCursor.run(None()).getOrElse { throw IllegalArgumentException("") }
}