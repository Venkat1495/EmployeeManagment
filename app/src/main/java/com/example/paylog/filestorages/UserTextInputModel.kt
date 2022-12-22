package com.example.paylog.filestorages

import android.content.Context
import java.io.File

class UserTextInputModel(_context: Context) {

    private var context: Context = _context
    private val textFileName = "userInput.txt"


    private fun makeFile(): File {
        return File(this.context.filesDir, this.textFileName)
    }

    fun saveText(s: String) {
        var file = this.makeFile()
        file.delete()
        file.writeText(s)
    }

    fun loadText(): String {
        var file = this.makeFile()
        var s = ""
        if(file.exists()) {
            s=file.readText()
        }
        return s
    }
}