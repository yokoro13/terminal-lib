package com.yokoro.terminal_lib.viewmodel

import androidx.lifecycle.ViewModel
import com.yokoro.terminal_lib.usecase.term.*

class TerminalViewModel (
    private val addNewRow: AddNewRow,
    private val getRowCharArray: GetRowCharArray,
    private val resize: Resize,
    private val setColor: SetColor,
    private val setText: SetText
    ): ViewModel() {

    suspend fun addNewRow(warp: Boolean = false) =
        addNewRow.run(AddNewRow.Params(warp))

    suspend fun getRowCharArray(y: Int) =
        getRowCharArray.run(GetRowCharArray.Params(y))

    suspend fun resize(newScreenColumnSize: Int, newScreenRowSize: Int) =
        resize.run(Resize.Params(newScreenColumnSize, newScreenRowSize))

    suspend fun setColor(x: Int, y: Int, color: Int) =
        setColor.run(SetColor.Params(x, y, color))

    suspend fun setText(x: Int, y: Int, text: Char) =
        setText.run(SetText.Params(x, y, text))


}