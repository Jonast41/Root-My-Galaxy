package dev.busung.s25uroot.log

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object LogExporter {
    fun share(context: Context) {
        if (!LogBuffer.hasContent) {
            Toast.makeText(context, "No log to export yet.", Toast.LENGTH_SHORT).show()
            return
        }
        val dir = File(context.cacheDir, "logs").also { it.mkdirs() }
        val fileName = "rmg_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.txt"
        val file = File(dir, fileName)
        file.writeText(LogBuffer.dump())

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.log_provider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Root My Galaxy log")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Export log…"))
    }
}