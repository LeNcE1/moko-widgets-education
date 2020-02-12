package org.example.mpp

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.DialogFragment
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.widgets.screen.Screen
import kotlinx.android.parcel.Parcelize
import kotlin.properties.ReadOnlyProperty

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

class AlertDialogFragment : DialogFragment() {
    var listener: Listener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val arg = arguments?.getParcelable<Arg>(ARG_KEY)!!

        return AlertDialog.Builder(context)
            .setTitle(arg.title)
            .setMessage(arg.message)
            .apply {
                if (arg.positiveButton != null) {
                    setPositiveButton(arg.positiveButton) { _, _ ->
                        listener?.onPositivePressed(arg.dialogId)
                    }
                }
                if (arg.negativeButton != null) {
                    setNegativeButton(arg.negativeButton) { _, _ ->
                        listener?.onNegativePressed(arg.dialogId)
                    }
                }
            }
            .setCancelable(true)
            .create()
    }

    interface Listener {
        fun onPositivePressed(dialogId: Int)
        fun onNegativePressed(dialogId: Int)
    }

    @Parcelize
    data class Arg(
        val dialogId: Int,
        val title: String,
        val message: String,
        val positiveButton: String?,
        val negativeButton: String?
    ) : Parcelable

    companion object {
        private const val ARG_KEY = "arg"

        fun instantiate(arg: Arg): AlertDialogFragment {
            return AlertDialogFragment().apply {
                arguments = Bundle().apply { putParcelable(ARG_KEY, arg) }
            }
        }
    }
}

actual fun Screen<*>.showDialog(
    dialogId: Int,
    title: StringDesc,
    message: StringDesc,
    positiveButton: StringDesc?,
    negativeButton: StringDesc?,
    buttonsHandler: DialogButtonsHandler // it's just marker
) {
    val context = requireContext()
    AlertDialogFragment
        .instantiate(
            AlertDialogFragment.Arg(
                dialogId,
                title.toString(context),
                message.toString(context),
                positiveButton?.toString(context),
                negativeButton?.toString(context)
            )
        )
        .show(childFragmentManager, null)
}

actual class DialogButtonsHandler

actual fun Screen<*>.registerDialogButtonsHandler(
    onPositivePressed: (dialogId: Int) -> Unit,
    onNegativePressed: (dialogId: Int) -> Unit
): ReadOnlyProperty<Screen<*>, DialogButtonsHandler> {
    return registerAttachFragmentHook(DialogButtonsHandler()) {
        if (it is AlertDialogFragment) {
            it.listener = object : AlertDialogFragment.Listener {
                override fun onPositivePressed(dialogId: Int) {
                    onPositivePressed(dialogId)
                }

                override fun onNegativePressed(dialogId: Int) {
                    onNegativePressed(dialogId)
                }
            }
        }
    }
}

actual fun Screen<*>.registerPhonePickerHandler(
    code: Int,
    handler: (phone: String) -> Unit
): ReadOnlyProperty<Screen<*>, PhonePickerHandler> {
    return registerActivityResultHook(
        requestCode = code,
        value = PhonePickerHandler(code)
    ) { result, data ->
        if (result == Activity.RESULT_OK) {
            val contactUri = data?.data ?: return@registerActivityResultHook

            val contentResolver = requireContext().contentResolver
            val projection = arrayOf(ContactsContract.Contacts._ID)
            val cursor = contentResolver.query(
                contactUri, projection,
                null, null, null
            )

            if (cursor != null && cursor.moveToFirst()) {
                val idIdx = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                val id = cursor.getInt(idIdx)

                val phones = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null
                )
                if (phones?.moveToFirst() == true) {
                    val numberIdx = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val number = phones.getString(numberIdx)

                    handler(number)
                }
                phones?.close()
            }
            cursor?.close()
        }
    }
}

actual class PhonePickerHandler(val requestCode: Int)

actual fun Screen<*>.showPhonePicker(pickerHandler: PhonePickerHandler) {
    val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
    startActivityForResult(intent, pickerHandler.requestCode)
}