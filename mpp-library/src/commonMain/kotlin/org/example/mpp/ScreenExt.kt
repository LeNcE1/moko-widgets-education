package org.example.mpp

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.widgets.screen.Screen
import kotlin.properties.ReadOnlyProperty

expect fun Screen<*>.openUrl(url: String)

expect fun Screen<*>.showMessage(title: StringDesc, message: StringDesc)

expect fun Screen<*>.showDialog(
    dialogId: Int,
    title: StringDesc,
    message: StringDesc,
    positiveButton: StringDesc?,
    negativeButton: StringDesc?,
    buttonsHandler: DialogButtonsHandler
)

expect class DialogButtonsHandler

expect fun Screen<*>.registerDialogButtonsHandler(
    onPositivePressed: (dialogId: Int) -> Unit,
    onNegativePressed: (dialogId: Int) -> Unit
): ReadOnlyProperty<Screen<*>, DialogButtonsHandler>

expect class PhonePickerHandler

expect fun Screen<*>.registerPhonePickerHandler(
    code: Int,
    handler: (phone: String) -> Unit
): ReadOnlyProperty<Screen<*>, PhonePickerHandler>

expect fun Screen<*>.showPhonePicker(
    pickerHandler: PhonePickerHandler
)