package org.example.mpp.profile

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.ButtonWidget
import dev.icerock.moko.widgets.button
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Resultable
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.route
import dev.icerock.moko.widgets.style.view.WidgetSize

class EditProfileScreen(
    private val theme: Theme,
    private val routeBack: Route<Unit>
) : WidgetScreen<Args.Empty>(), Resultable<EditProfileScreen.Result>, NavigationItem {

    override val navigationBar: NavigationBar get() = NavigationBar.Normal("Edit profile".desc())

    override var screenResult: Result? = null

    override fun createContentWidget() = with(theme) {
        constraint(size = WidgetSize.AsParent) {
            val close1Btn = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(
                    Value.data(
                        "Close not edited".desc()
                    )
                )
            ) {
                screenResult = Result(false)
                routeBack.route(this@EditProfileScreen)
            }

            val close2Btn = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(
                    Value.data(
                        "Close edited".desc()
                    )
                )
            ) {
                screenResult = Result(true)
                routeBack.route(this@EditProfileScreen)
            }

            constraints {
                close1Btn centerYToCenterY root
                close1Btn centerXToCenterX root

                close2Btn centerXToCenterX root
                close2Btn topToBottom close1Btn
            }
        }
    }

    @Parcelize
    data class Result(val edited: Boolean) : Parcelable
}