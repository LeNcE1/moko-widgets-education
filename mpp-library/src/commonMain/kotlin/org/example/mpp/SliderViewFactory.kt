package org.example.mpp

import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.style.view.WidgetSize

expect class SliderViewFactory() : ViewFactory<SliderWidget<out WidgetSize>>