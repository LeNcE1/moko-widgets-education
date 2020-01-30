package org.example.mpp.auth

import dev.icerock.moko.widgets.ButtonWidget
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.navigation.Route

class AuthFactory(
    private val theme: Theme,
    private val submitButtons: ButtonWidget.Category
) {
    fun createInputPhoneScreen(routeInputCode: Route<String>): InputPhoneScreen {
        return InputPhoneScreen(
            theme = theme,
            viewModelFactory = { InputPhoneViewModel(it) },
            routeInputCode = routeInputCode,
            submitButtons = submitButtons
        )
    }

    fun createInputCodeScreen(routeMain: Route<Unit>): InputCodeScreen {
        return InputCodeScreen(
            theme = theme,
            viewModelFactory = { eventsDispatcher, token ->
                InputCodeViewModel(eventsDispatcher, token)
            },
            routeMain = routeMain,
            submitButtons = submitButtons
        )
    }
}