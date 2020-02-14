package org.example.mpp.info

import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route

expect class PlatformInfoScreen(
    theme: Theme,
    routeProfile: Route<Unit>
) : InfoScreen