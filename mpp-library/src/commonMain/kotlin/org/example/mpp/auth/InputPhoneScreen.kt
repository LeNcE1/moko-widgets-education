package org.example.mpp.auth

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.ButtonWidget
import dev.icerock.moko.widgets.InputWidget
import dev.icerock.moko.widgets.button
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.input
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.listen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.style.view.WidgetSize

class InputPhoneScreen(
    private val theme: Theme,
    private val viewModelFactory: (
        EventsDispatcher<InputPhoneViewModel.EventsListener>
    ) -> InputPhoneViewModel,
    private val routeInputCode: Route<String>
) : WidgetScreen<Args.Empty>(), InputPhoneViewModel.EventsListener, NavigationItem {

    override val navigationBar: NavigationBar get() = NavigationBar.Normal("Input phone".desc())

    override fun createContentWidget() = with(theme) {
        val viewModel = getViewModel {
            viewModelFactory(createEventsDispatcher())
        }

        viewModel.eventsDispatcher.listen(this@InputPhoneScreen, this@InputPhoneScreen)

        constraint(size = WidgetSize.AsParent) {
            val nameInput = +input(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                id = Ids.Phone,
                label = const("Phone"),
                field = viewModel.phoneField
            )

            val submitButton = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Submit".desc())),
                onTap = viewModel::onSubmitPressed
            )

            constraints {
                nameInput centerYToCenterY root
                nameInput leftRightToLeftRight root offset 16

                submitButton bottomToBottom root.safeArea offset 16
                submitButton leftRightToLeftRight root offset 16
            }
        }
    }

    object Ids {
        object Phone : InputWidget.Id
    }

    override fun routeInputCode(token: String) {
        routeInputCode.route(this, token)
    }
}

class InputPhoneViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel(), EventsDispatcherOwner<InputPhoneViewModel.EventsListener> {

    val phoneField: FormField<String, StringDesc> = FormField(
        initialValue = "",
        validation = liveBlock { null }
    )

    fun onSubmitPressed() {
        val token = "token:" + phoneField.data.value
        eventsDispatcher.dispatchEvent { routeInputCode(token) }
    }

    interface EventsListener {
        fun routeInputCode(token: String)
    }
}