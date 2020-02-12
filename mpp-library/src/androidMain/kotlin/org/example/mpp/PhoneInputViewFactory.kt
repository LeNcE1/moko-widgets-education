package org.example.mpp

import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.EditText
import dev.icerock.moko.widgets.InputWidget
import dev.icerock.moko.widgets.core.ViewBundle
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.style.applyInputType
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.utils.androidId
import dev.icerock.moko.widgets.utils.bind

actual class PhoneInputViewFactory : ViewFactory<InputWidget<out WidgetSize>> {
    override fun <WS : WidgetSize> build(
        widget: InputWidget<out WidgetSize>,
        size: WS,
        viewFactoryContext: ViewFactoryContext
    ): ViewBundle<WS> {
        val context = viewFactoryContext.androidContext
        val lifecycleOwner = viewFactoryContext.lifecycleOwner

        val editText = EditText(context).apply {
            id = widget.id.androidId

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            widget.inputType?.also { applyInputType(it) }

            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) widget.field.validate()
            }
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s == null) return

                    widget.field.data.value = s.toString()
                }
            })
        }

        widget.field.data.bind(lifecycleOwner) { data ->
            if (editText.text?.toString() == data) return@bind

            editText.setText(data)
        }

        widget.label.bind(lifecycleOwner) { editText.hint = it?.toString(context) }
        widget.enabled?.bind(lifecycleOwner) { editText.isEnabled = it == true }
        widget.maxLines?.bind(lifecycleOwner) { maxLines ->
            when (maxLines) {
                null -> editText.setSingleLine(false)
                1 -> editText.setSingleLine(true)
                else -> {
                    editText.setSingleLine(false)
                    editText.maxLines = maxLines
                }
            }
        }

        return ViewBundle(
            view = editText,
            size = size,
            margins = null
        )
    }
}