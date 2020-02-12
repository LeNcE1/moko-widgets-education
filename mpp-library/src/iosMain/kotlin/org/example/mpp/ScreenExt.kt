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

actual fun Screen<*>.showDialog(
    dialogId: Int,
    title: StringDesc,
    message: StringDesc,
    positiveButton: StringDesc?,
    negativeButton: StringDesc?,
    buttonsHandler: DialogButtonsHandler
) {
    val alertController = UIAlertController.alertControllerWithTitle(
        title = title.localized(),
        message = message.localized(),
        preferredStyle = UIAlertControllerStyleAlert
    )
    if (positiveButton != null) {
        UIAlertAction.actionWithTitle(
            title = positiveButton.localized(),
            style = UIAlertActionStyleDefault,
            handler = { buttonsHandler.onPositivePressed(dialogId) }
        ).let { alertController.addAction(it) }
    }
    if (negativeButton != null) {
        UIAlertAction.actionWithTitle(
            title = negativeButton.localized(),
            style = UIAlertActionStyleDestructive,
            handler = { buttonsHandler.onNegativePressed(dialogId) }
        ).let { alertController.addAction(it) }
    }
    viewController.presentViewController(alertController, animated = true, completion = null)
}

actual class DialogButtonsHandler(
    val onPositivePressed: (dialogId: Int) -> Unit,
    val onNegativePressed: (dialogId: Int) -> Unit
)

actual fun Screen<*>.registerDialogButtonsHandler(
    onPositivePressed: (dialogId: Int) -> Unit,
    onNegativePressed: (dialogId: Int) -> Unit
): ReadOnlyProperty<Screen<*>, DialogButtonsHandler> {
    return createConstReadOnlyProperty(
        DialogButtonsHandler(
            onPositivePressed = onPositivePressed,
            onNegativePressed = onNegativePressed
        )
    )
}

actual class PhonePickerHandler(
    val onSelected: (phone: String) -> Unit
) : NSObject(), CNContactPickerDelegateProtocol {
    override fun contactPicker(picker: CNContactPickerViewController, didSelectContact: CNContact) {
        val numbers = didSelectContact.phoneNumbers as List<CNLabeledValue>
        val firstNumber = numbers.firstOrNull() ?: return
        val cnPhoneNumber = firstNumber.value as CNPhoneNumber
        onSelected(cnPhoneNumber.stringValue)
    }
}

actual fun Screen<*>.registerPhonePickerHandler(
    code: Int,
    handler: (phone: String) -> Unit
): ReadOnlyProperty<Screen<*>, PhonePickerHandler> {
    return createConstReadOnlyProperty(PhonePickerHandler(handler))
}

actual fun Screen<*>.showPhonePicker(pickerHandler: PhonePickerHandler) {
    val contactPicker = CNContactPickerViewController()
    contactPicker.delegate = pickerHandler

    viewController.presentViewController(contactPicker, animated = true, completion = null)
}