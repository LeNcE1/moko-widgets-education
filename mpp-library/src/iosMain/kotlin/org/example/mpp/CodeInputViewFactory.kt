package org.example.mpp

import cocoapods.SVPinView.SVPinView
import dev.icerock.moko.widgets.InputWidget
import dev.icerock.moko.widgets.core.ViewBundle
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.style.view.WidgetSize
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.UIKit.heightAnchor
import platform.UIKit.translatesAutoresizingMaskIntoConstraints

actual class CodeInputViewFactory actual constructor() : ViewFactory<InputWidget<out WidgetSize>> {
    override fun <WS : WidgetSize> build(
        widget: InputWidget<out WidgetSize>,
        size: WS,
        viewFactoryContext: ViewFactoryContext
    ): ViewBundle<WS> {

        val textField = SVPinView(frame = CGRectZero.readValue()).apply {
            translatesAutoresizingMaskIntoConstraints = false

            setPlaceholder("****")

            heightAnchor.constraintEqualToConstant(80.0).active = true
        }

//        textField.setEventHandler(UIControlEventEditingChanged) {
//            val currentValue = widget.field.data.value
//            val newValue = textField.text
//
//            if (currentValue != newValue) {
//                widget.field.data.value = newValue.orEmpty()
//            }
//        }

//        widget.field.data.bind { textField.text = it }

        return ViewBundle(
            view = textField,
            size = size,
            margins = null
        )
    }
}