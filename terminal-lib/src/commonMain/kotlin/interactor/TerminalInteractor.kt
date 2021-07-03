package interactor

import entity.Cursor
import entity.ScreenSize
import entity.TerminalArray
import entity.TerminalChar
import repository.ITerminalRepository
import usecase.UseCaseInputPort
import usecase.UseCaseOutputPort
import usecase.cursor.*
import usecase.escapesequence.EscapeSequenceUseCase
import usecase.screen.ScreenUseCase
import usecase.terminal.*
import usecase.terminalbuffer.*

class TerminalInteractor(

): UseCaseInputPort {
    lateinit var terminalRepository: ITerminalRepository
    lateinit var output: UseCaseOutputPort

    private var escString: String = ""

    private var opCode: List<Char> = listOf<Char>('\n', '\r', '\t', '\b')

    private val terminalBufferUseCase = TerminalBufferUseCase(
        AddNewRow(terminalRepository),
        GetTerminalBuffer(terminalRepository),
        SetTerminalChar(terminalRepository),
        SetChar(terminalRepository),
        SetForeColor(terminalRepository),
        SetBackColor(terminalRepository)
    )

    private val cursorUseCase = CursorUseCase(
        SetCursor(terminalRepository),
        GetCursor(terminalRepository),
        SetDisplayingState(terminalRepository),
        IsDisplaying(terminalRepository)
    )

    private val terminalUseCase = TerminalUseCase(
        CreateTerminal(terminalRepository),
        Resize(terminalRepository),
        GetScreenSize(terminalRepository),
        SetTopRow(terminalRepository),
        GetTopRow(terminalRepository),
        terminalBufferUseCase
    )

    private val screenUseCase = ScreenUseCase(
        terminalBufferUseCase,
        terminalUseCase,
        cursorUseCase
    )

    private val escapeSequenceUseCase = EscapeSequenceUseCase(
        GetScreenSize(terminalRepository),
        GetTopRow(terminalRepository),
        SetTopRow(terminalRepository),
        cursorUseCase,
        terminalBufferUseCase
    )

    override fun setTerminalChar(x: Int, y: Int, terminalChar: TerminalChar) {
        terminalBufferUseCase.setChar(x, y, terminalChar.char)
        terminalBufferUseCase.setForeColor(x, y, terminalChar.foreColor)
        terminalBufferUseCase.setBackColor(x, y, terminalChar.backColor)
    }

    override fun setText(x: Int, y: Int, text: Char) {
        terminalBufferUseCase.setChar(x, y, text)
    }

    override fun setCursor(cursor: Cursor) {
        cursorUseCase.setCursor(cursor.x, cursor.y)
    }

    override fun setScreenSize(screenSize: ScreenSize) {
        terminalUseCase.resize(screenSize)
    }

    override fun setForeColor(x: Int, y: Int, foreColor: Int) {
        terminalBufferUseCase.setForeColor(x, y, foreColor)
    }

    override fun setBackColor(x: Int, y: Int, backColor: Int) {
        terminalBufferUseCase.setBackColor(x, y, backColor)
    }

    override fun getText(x: Int, y: Int): TerminalChar {
        val screenText = getScreenText()
        return output.getText(screenText[x].terminalRow[y])
    }

    override fun getScreenText(): ArrayList<TerminalArray> {
        return output.getScreenText(terminalBufferUseCase.getTerminalBuffer())
    }

    override fun getCursor(): Cursor {
        return output.getCursor(cursorUseCase.getCursor())
    }

    override fun getScreenSize(): ScreenSize {
        return output.getScreenSize(terminalUseCase.getScreenSize())
    }

    override fun writeCharOrRunEscSeq(text: String) {
        text.forEach { checkEscapeSequenceCharAndRun(it) }
    }

    override fun scrollDown(n: Int) {
        screenUseCase.scrollDown()
    }

    override fun scrollUp(n: Int) {
        screenUseCase.scrollUp()
    }

    fun runOperationCode(code: Char) {
        when (code) {
            '\r' -> cursorUseCase.setCursor(0, getCursor().y)
            '\n' -> {
                cursorUseCase.setCursor(getCursor().x, getCursor().y + 1)
            }
            '\b' -> {
                cursorUseCase.setCursor(getCursor().x-1, getCursor().y)
            }
            '\t' -> {
                val move = 4 - 4 % getCursor().x
                cursorUseCase.setCursor(getCursor().x+move, getCursor().y)
            }
        }
    }

    fun writeCharAtCursor(text: Char) {
        if (opCode.contains(text)) {
            runOperationCode(text)
        } else {
            terminalBufferUseCase.setChar(getCursor().x, getCursor().y + terminalUseCase.getTopRow(), text)
        }
    }

    fun checkEscapeSequenceCharAndRun(text: Char) {
        escString += text
        if (!isEscapeSequence(escString)) {
            invalidEscapeSequence()
            return
        }

        if (escString.matches("$[A-HJKSTfm]".toRegex())) {
            escapeSequence(escString)
            escString = ""
        }
    }

    private fun isEscapeSequence(sequence: String): Boolean {
        return sequence.matches("(^\\u001b)".toRegex())
                || sequence.matches("(^\\u001b\\[)".toRegex())
                || sequence.matches("(^\\u001b\\[)(\\d*)".toRegex())
                || sequence.matches("(^\\u001b\\[)(\\d*);".toRegex())
                || sequence.matches("(^\\u001b\\[)(\\d*);(\\d*)".toRegex())
                || sequence.matches("(^\\u001b\\[)(\\d*)([A-GJKSTm])".toRegex())
                || sequence.matches("(^\\u001b\\[)(\\d*);(\\d*)([Hf])".toRegex())
    }

    private fun invalidEscapeSequence(){
        escString.forEach { writeCharAtCursor(it) }
        escString = ""
    }

    private fun escapeSequence(sequence: String) {
        val length = sequence.length
        val mode = sequence[length - 1]
        var n = 1
        var m = 1
        val semicolonPos: Int

        if (length != 2) {
            if (mode != 'H') {
                n = sequence.substring(1, length - 1).toInt()
            } else {
                semicolonPos = sequence.indexOf(";")
                if (semicolonPos != 1) {
                    n = sequence.substring(1, semicolonPos).toInt()
                }
                if (sequence[semicolonPos + 1] != 'H' || sequence[semicolonPos + 1] != 'f') {
                    m = sequence.substring(semicolonPos + 1, length - 1).toInt()
                }
            }
        }

        when (mode) {
            'A' -> escapeSequenceUseCase.moveUp(getCursor(), n)
            'B' -> escapeSequenceUseCase.moveDown(getCursor(), n)
            'C' -> escapeSequenceUseCase.moveRight(getCursor(), n)
            'D' -> escapeSequenceUseCase.moveLeft(getCursor(), n)
            'E' -> escapeSequenceUseCase.moveDownToLineHead(getCursor(), n)
            'F' -> escapeSequenceUseCase.moveUpToLineHead(getCursor(), n)
            'G' -> escapeSequenceUseCase.moveCursor(getCursor(), n)
            'H', 'f' -> escapeSequenceUseCase.moveCursor(getCursor(), n, m)
            'J' -> escapeSequenceUseCase.clearScreen(getCursor(), n-1)
            'K' -> escapeSequenceUseCase.clearLine(getCursor(), n-1)
            'S' -> escapeSequenceUseCase.scrollNext(n)
            'T' -> escapeSequenceUseCase.scrollBack(n)
            'm' -> escapeSequenceUseCase.selectGraphicRendition(n)
        }
    }
}