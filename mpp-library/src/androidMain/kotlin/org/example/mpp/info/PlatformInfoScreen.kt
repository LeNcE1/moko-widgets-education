package org.example.mpp.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route
import org.example.mpp.R

actual class PlatformInfoScreen actual constructor(
    theme: Theme,
    routeProfile: Route<Unit>
) : InfoScreen(theme, routeProfile) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.screen_info, container, false)
        view.findViewById<Button>(R.id.profile_btn).setOnClickListener {
            onProfileButtonPressed()
        }
        return view
    }
}