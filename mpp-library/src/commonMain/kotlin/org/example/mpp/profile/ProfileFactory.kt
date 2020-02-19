package org.example.mpp.profile

import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.RouteWithResult
import org.example.mpp.friends.FriendsScreen

class ProfileFactory(
    private val theme: Theme
) {
    fun createProfileScreen(
        routeEdit: RouteWithResult<Unit, Boolean>,
        routeLogout: Route<Unit>,
        routeFriends: Route<Unit>
    ): ProfileScreen {
        return ProfileScreen(
            theme = theme,
            routeEdit = routeEdit,
            routeLogout = routeLogout,
            routeFriends = routeFriends
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

    fun createFriendsScreen(): FriendsScreen {
        return FriendsScreen(theme = theme)
    }
}