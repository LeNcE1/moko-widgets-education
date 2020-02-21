package org.example.mpp.profile

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
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
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.RouteWithResult
import dev.icerock.moko.widgets.screen.navigation.registerRouteHandler
import dev.icerock.moko.widgets.screen.navigation.route
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.text
import org.example.mpp.SliderWidget
import org.example.mpp.slider

class ProfileScreen(
    private val theme: Theme,
    private val routeEdit: RouteWithResult<Unit, Boolean>,
    private val routeLogout: Route<Unit>,
    private val routeFriends: Route<Unit>
) : WidgetScreen<Args.Empty>(), NavigationItem {

    override val navigationBar: NavigationBar get() = NavigationBar.Normal("Profile".desc())

    private val editHandler by registerRouteHandler(code = 9, route = routeEdit) {
        println("profile edited: $it")
    }

    override fun createContentWidget() = with(theme) {
        val sliderValue = MutableLiveData<Int>(initialValue = 0)

        constraint(size = WidgetSize.AsParent) {
            val editButton = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Edit".desc()))
            ) {
                routeEdit.route(this@ProfileScreen, editHandler)
            }

            val logoutButton = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Logout".desc()))
            ) {
                routeLogout.route()
            }

            val friendsButton = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Friends".desc()))
            ) {
                routeFriends.route()
            }

            val slider = +slider(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                id = Ids.Slider,
                minValue = -5,
                maxValue = 5,
                value = sliderValue
            )

            val valueText = +text(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                text = sliderValue.map { it.toString().desc() as StringDesc }
            )

            constraints {
                editButton centerYToCenterY root
                editButton centerXToCenterX root

                logoutButton centerXToCenterX root
                logoutButton topToBottom editButton

                friendsButton topToBottom logoutButton
                friendsButton centerXToCenterX root

                slider bottomToTop editButton offset 16
                slider leftRightToLeftRight root

                valueText bottomToTop slider offset 16
                valueText leftRightToLeftRight root
            }
        }
    }

    object Ids {
        object Slider : SliderWidget.Id
    }
}