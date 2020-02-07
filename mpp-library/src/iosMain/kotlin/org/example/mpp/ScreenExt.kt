package org.example.mpp

import dev.icerock.moko.widgets.screen.Screen
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun Screen<*>.openUrl(url: String) {
    UIApplication.sharedApplication.openURL(NSURL.URLWithString(url)!!)
}

actual fun Screen<*>.showMessage(
    title: StringDesc,
    message: StringDesc
) {
    val alertController = UIAlertController.alertControllerWithTitle(
        title = title.localized(),
        message = message.localized(),
        preferredStyle = UIAlertControllerStyleAlert
    )
    alertController.addAction(
        UIAlertAction.actionWithTitle(
            title = "Cancel",
            style = UIAlertActionStyleCancel,
            handler = null
        )
    )
    viewController.presentViewController(alertController, animated = true, completion = null)
}