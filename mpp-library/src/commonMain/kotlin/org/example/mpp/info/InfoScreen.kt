package org.example.mpp.info

import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.Screen
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.route

abstract class InfoScreen(
    private val theme: Theme,
    private val routeProfile: Route<Unit>
) : Screen<Args.Empty>() {

    fun onProfileButtonPressed() {
        routeProfile.route()
    }
}