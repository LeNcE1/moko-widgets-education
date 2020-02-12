package org.example.mpp

import android.view.ViewGroup
import com.goodiebag.pinview.Pinview
import dev.icerock.moko.widgets.InputWidget
import dev.icerock.moko.widgets.core.ViewBundle
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.utils.androidId
import dev.icerock.moko.widgets.utils.bind

actual class CodeInputViewFactory actual constructor() : ViewFactory<InputWidget<out WidgetSize>> {
    override fun <WS : WidgetSize> build(
        widget: InputWidget<out WidgetSize>,
        size: WS,
        viewFactoryContext: ViewFactoryContext
    ): ViewBundle<WS> {
        val context = viewFactoryContext.androidContext
        val lifecycleOwner = viewFactoryContext.lifecycleOwner

        val editText = Pinview(context).apply {
            id = widget.id.androidId

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) widget.field.validate()
            }
            setPinViewEventListener { pinview, fromUser ->
                widget.field.data.value = pinview.value
                widget.field.validate()
            }
        }

        widget.field.data.bind(lifecycleOwner) { data ->
            if (editText.value == data) return@bind

            editText.value = data
        }

        widget.enabled?.bind(lifecycleOwner) { editText.isEnabled = it == true }

        return ViewBundle(
            view = editText,
            size = size,
            margins = null
        )
    }
}