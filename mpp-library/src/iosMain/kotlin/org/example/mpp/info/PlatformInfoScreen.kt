package org.example.mpp.info

import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route
import platform.UIKit.UIViewController
import cocoapods.mppLibraryIos.*

actual class PlatformInfoScreen actual constructor(
    theme: Theme,
    routeProfile: Route<Unit>
) : InfoScreen(theme, routeProfile) {

    override fun createViewController(): UIViewController {
        val vc = InfoViewController.create()
        vc.setOnProfileButtonPressed {
            onProfileButtonPressed()
        }
        return vc
    }
}