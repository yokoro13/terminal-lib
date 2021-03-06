package repository

import core.Either
import core.Failure
import core.getOrElse
import entity.Cursor
import entity.ScreenSize
import entity.TerminalArray
import kotlin.test.Test
import kotlin.test.assertEquals

class TerminalRepositoryTest {

    private fun <T> getOrError(a: Either<Failure, T>): T =
        a.getOrElse { throw IllegalArgumentException("") }

    @Test
    fun testCreateTerminal() {
        val terminalRepository = TerminalRepository()
        val ss = ScreenSize(48, 20)
        terminalRepository.createTerminal(ss)
        assertEquals(1, getOrError(terminalRepository.getTerminalBuffer()).size)
    }

    private fun getLineText(terminalArray: TerminalArray): String {
        var text: String = ""
        terminalArray.terminalRow.forEach { text+=it.char }
        return text
    }

    private fun String.addEmptyToScreenColumnSize(column: Int): String {
        val size = this.length
        var result = this
        for (i in size until column) {
            result += ' '
        }
        return result
    }

    @Test
    fun testResize() {
        val terminalRepository = TerminalRepository()
        terminalRepository.createTerminal(ScreenSize(20, 20))

        terminalRepository.setChar(0, 0, 'a')
        terminalRepository.setChar(1, 0, 'a')
        terminalRepository.setChar(2, 0, 'a')
        terminalRepository.setChar(3, 0, 'a')
        terminalRepository.setChar(4, 0, 'a')
        terminalRepository.setChar(5, 0, 'a')


        println("*** resize screen size to small ***")
        terminalRepository.resize(ScreenSize(1, 1))
        var topRow = getOrError(terminalRepository.getTopRow())
        var currentLine = topRow + getOrError(terminalRepository.getCursor()).y
        var resizedTerminal = getOrError(terminalRepository.getTerminalBuffer())
        println("**currentLine")
        assertEquals(5, currentLine)
        println("**topRow")
        assertEquals(5, getOrError(terminalRepository.getTopRow()))
        println("**buffer")
        assertEquals("a", getLineText(resizedTerminal[currentLine]))


        println("*** resize row to large ***")
        terminalRepository.resize(ScreenSize(1, 5))
        topRow = getOrError(terminalRepository.getTopRow())
        currentLine = topRow + getOrError(terminalRepository.getCursor()).y
        resizedTerminal = getOrError(terminalRepository.getTerminalBuffer())
        println("**currentLine")
        assertEquals(5, currentLine)
        println("**topRow")
        assertEquals(1, topRow)
        println("**buffer")
        assertEquals("a".addEmptyToScreenColumnSize(1), getLineText(resizedTerminal[currentLine]))


        println("*** resize to original size ***")
        terminalRepository.resize(ScreenSize(20, 20))
        topRow = getOrError(terminalRepository.getTopRow())
        currentLine = topRow + getOrError(terminalRepository.getCursor()).y
        resizedTerminal = getOrError(terminalRepository.getTerminalBuffer())
        println("**currentLine")
        assertEquals(0, currentLine)
        println("**topRow")
        assertEquals(0, topRow)
        println("**buffer")
        assertEquals("aaaaaa".addEmptyToScreenColumnSize(20), getLineText(resizedTerminal[currentLine]))
    }


    @Test
    fun testSetCursor() {
        val terminalRepository = TerminalRepository()
        terminalRepository.createTerminal(ScreenSize(5, 5))

        terminalRepository.setCursor(3, 3)
        var cursor = getOrError(terminalRepository.getCursor())
        assertEquals(Cursor(3, 3), cursor)

        terminalRepository.setCursor(-1, -1)
        cursor = getOrError(terminalRepository.getCursor())
        assertEquals(Cursor(0, 0), cursor)

        terminalRepository.setCursor(100, 100)
        cursor = getOrError(terminalRepository.getCursor())
        assertEquals(Cursor(4, 4), cursor)
    }

}