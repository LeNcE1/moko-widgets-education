package org.example.mpp

import dev.icerock.moko.widgets.InputWidget
import dev.icerock.moko.widgets.core.ViewBundle
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.objc.setAssociatedObject
import dev.icerock.moko.widgets.style.applyInputTypeIfNeeded
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.utils.*
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UIControlEventEditingChanged
import platform.UIKit.UITextField
import platform.UIKit.clipsToBounds
import platform.UIKit.translatesAutoresizingMaskIntoConstraints


actual class PhoneInputViewFactory : ViewFactory<InputWidget<out WidgetSize>> {
    override fun <WS : WidgetSize> build(
        widget: InputWidget<out WidgetSize>,
        size: WS,
        viewFactoryContext: ViewFactoryContext
    ): ViewBundle<WS> {

        val textField = UITextField(frame = CGRectZero.readValue()).apply {
            translatesAutoresizingMaskIntoConstraints = false
            applyInputTypeIfNeeded(widget.inputType)

            clipsToBounds = true
        }

        val mask = widget.inputType?.mask
        if (mask != null) {
            val delegate = DefaultFormatterUITextFieldDelegate(
                inputFormatter = DefaultTextFormatter(
                    textPattern = mask.toIosPattern(),
                    patternSymbol = '#'
                )
            )
            textField.delegate = delegate
            setAssociatedObject(textField, delegate)
        }

        textField.setEventHandler(UIControlEventEditingChanged) {
            val currentValue = widget.field.data.value
            val newValue = textField.text

            if (currentValue != newValue) {
                widget.field.data.value = newValue.orEmpty()
            }
        }

        widget.enabled?.bind { textField.enabled = it }
        widget.label.bind { textField.placeholder = it.localized() }
        widget.field.data.bind { textField.text = it }

        return ViewBundle(
            view = textField,
            size = size,
            margins = null
        )
    }
}