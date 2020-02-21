package org.example.mpp

import android.widget.SeekBar
import dev.icerock.moko.widgets.core.ViewBundle
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.utils.bindNotNull

actual class SliderViewFactory : ViewFactory<SliderWidget<out WidgetSize>> {
    override fun <WS : WidgetSize> build(
        widget: SliderWidget<out WidgetSize>,
        size: WS,
        viewFactoryContext: ViewFactoryContext
    ): ViewBundle<WS> {
        val context = viewFactoryContext.androidContext
        val lifecycleOwner = viewFactoryContext.lifecycleOwner

        val slider = SeekBar(context).apply {
            max = widget.maxValue - widget.minValue
        }

        widget.value.bindNotNull(lifecycleOwner) { slider.progress = it - widget.minValue }

        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val fixedProgress = progress + widget.minValue
                if (widget.value.value == fixedProgress) return

                widget.value.value = fixedProgress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        return ViewBundle(
            view = slider,
            size = size,
            margins = null
        )
    }
}