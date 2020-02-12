package org.example.mpp

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