package org.example.mpp

import dev.icerock.moko.widgets.InputWidget
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.style.view.WidgetSize

expect class CodeInputViewFactory() : ViewFactory<InputWidget<out WidgetSize>>