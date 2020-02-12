package org.example.mpp


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