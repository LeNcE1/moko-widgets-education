package org.example.mpp

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.widgets.screen.Screen

actual fun Screen<*>.openUrl(url: String) {
    val context = requireContext()
    val openIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    if (openIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(openIntent)
    }
}

actual fun Screen<*>.showMessage(
    title: StringDesc,
    message: StringDesc
) {
    val context = requireContext()
    AlertDialog.Builder(context)
        .setTitle(title.toString(context))
        .setMessage(message.toString(context))
        .setPositiveButton(android.R.string.cancel) { _, _ -> }
        .setCancelable(true)
        .create()
        .show()
}