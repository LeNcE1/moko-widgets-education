package org.example.mpp

import dev.icerock.moko.widgets.InputWidget
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.style.view.WidgetSize

expect class PhoneInputViewFactory() : ViewFactory<InputWidget<out WidgetSize>>