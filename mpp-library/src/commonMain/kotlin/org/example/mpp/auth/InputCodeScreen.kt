package org.example.mpp.auth

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
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
import dev.icerock.moko.widgets.screen.getArgument
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.listen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.route
import dev.icerock.moko.widgets.style.view.WidgetSize

class InputCodeScreen(
    private val theme: Theme,
    private val viewModelFactory: (
        eventsDispatcher: EventsDispatcher<InputCodeViewModel.EventsListener>,
        token: String
    ) -> InputCodeViewModel,
    private val routeMain: Route<Unit>,
    private val submitButtons: ButtonWidget.Category
) : WidgetScreen<Args.Parcel<InputCodeScreen.Arg>>(), InputCodeViewModel.EventsListener,
    NavigationItem {

    override val navigationBar: NavigationBar get() = NavigationBar.Normal("Input code".desc())

    override fun createContentWidget() = with(theme) {
        val viewModel = getViewModel {
            viewModelFactory(createEventsDispatcher(), getArgument().token)
        }

        viewModel.eventsDispatcher.listen(this@InputCodeScreen, this@InputCodeScreen)

        constraint(size = WidgetSize.AsParent) {
            val nameInput = +input(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                id = Ids.Code,
                label = const("Code"),
                field = viewModel.codeField
            )

            val submitButton = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Submit".desc())),
                onTap = viewModel::onSubmitPressed,
                category = submitButtons
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
        object Code : InputWidget.Id
    }

    override fun routeMain() {
        routeMain.route(this)
    }

    @Parcelize
    data class Arg(val token: String) : Parcelable
}

class InputCodeViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val token: String
) : ViewModel(), EventsDispatcherOwner<InputCodeViewModel.EventsListener> {

    val codeField: FormField<String, StringDesc> = FormField(
        initialValue = token,
        validation = liveBlock { null }
    )

    fun onSubmitPressed() {
        eventsDispatcher.dispatchEvent { routeMain() }
    }

    interface EventsListener {
        fun routeMain()
    }
}