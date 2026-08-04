package dev.busung.s25uroot.log

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.CopyOnWriteArrayList

object LogBuffer {
    private val lines = CopyOnWriteArrayList<String>()
    private val timestampFmt = SimpleDateFormat("HH:mm:ss.SSS", Locale.US)
    private val dateFmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun append(line: String) {
        val ts = timestampFmt.format(Date())
        lines.add("[$ts] $line")
    }

    fun dump(): String {
        val header = "=== Root My Galaxy Log ===\nExported: ${dateFmt.format(Date())}\n\n"
        return header + lines.joinToString("\n")
    }

    fun clear() = lines.clear()

    val hasContent: Boolean get() = lines.isNotEmpty()
}