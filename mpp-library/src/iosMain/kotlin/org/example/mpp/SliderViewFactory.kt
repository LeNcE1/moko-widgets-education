package org.example.mpp

import dev.icerock.moko.widgets.core.ViewBundle
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.utils.bind
import dev.icerock.moko.widgets.utils.setEventHandler
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UISlider
import platform.UIKit.translatesAutoresizingMaskIntoConstraints

actual class SliderViewFactory : ViewFactory<SliderWidget<out WidgetSize>> {
    override fun <WS : WidgetSize> build(
        widget: SliderWidget<out WidgetSize>,
        size: WS,
        viewFactoryContext: ViewFactoryContext
    ): ViewBundle<WS> {
        val slider = UISlider(frame = CGRectZero.readValue()).apply {
            translatesAutoresizingMaskIntoConstraints = false

            minimumValue = widget.minValue.toFloat()
            maximumValue = widget.maxValue.toFloat()
        }

        widget.value.bind { slider.value = it.toFloat() }
        slider.setEventHandler(UIControlEventValueChanged) {
            val value = slider.value.toInt()
            slider.value = value.toFloat()

            if (widget.value.value == value) return@setEventHandler

            widget.value.value = value
        }

        return ViewBundle(
            view = slider,
            size = size,
            margins = null
        )
    }
}