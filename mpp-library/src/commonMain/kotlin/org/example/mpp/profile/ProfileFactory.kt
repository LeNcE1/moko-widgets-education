package org.example.mpp.profile

import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.RouteWithResult

class ProfileFactory(
    private val theme: Theme
) {
    fun createProfileScreen(
        routeEdit: RouteWithResult<Unit, Boolean>,
        routeLogout: Route<Unit>
    ): ProfileScreen {
        return ProfileScreen(
            theme = theme,
            routeEdit = routeEdit,
            routeLogout = routeLogout
        )
    }

    fun createEditProfileScreen(
        routeBack: Route<Unit>
    ): EditProfileScreen {
        return EditProfileScreen(
            theme = theme,
            routeBack = routeBack
        )
    }
}