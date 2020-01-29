package org.example.mpp.info

import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.ButtonWidget
import dev.icerock.moko.widgets.button
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.route
import dev.icerock.moko.widgets.style.view.WidgetSize

class InfoScreen(
    private val theme: Theme,
    private val routeProfile: Route<Unit>
) : WidgetScreen<Args.Empty>() {

    override fun createContentWidget() = with(theme) {
        constraint(size = WidgetSize.AsParent) {
            val submitButton = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Profile".desc()))
            ) {
                routeProfile.route(this@InfoScreen)
            }

            constraints {
                submitButton centerYToCenterY root
                submitButton centerXToCenterX root
            }
        }
    }
}