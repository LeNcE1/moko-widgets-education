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
import dev.icerock.moko.widgets.screen.*
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.style.view.WidgetSize
import org.example.mpp.*

class InputPhoneScreen(
    private val theme: Theme,
    private val viewModelFactory: (
        EventsDispatcher<InputPhoneViewModel.EventsListener>
    ) -> InputPhoneViewModel,
    private val routeInputCode: Route<String>,
    private val submitButtons: ButtonWidget.Category
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

            val githubButton = +button(
                size = WidgetSize.WrapContent,
                content = ButtonWidget.Content.Text(Value.data("GitHub".desc())),
                onTap = ::onGitHubPressed
            )

            val submitButton = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Submit".desc())),
                onTap = viewModel::onSubmitPressed,
                category = submitButtons
            )

            val aboutButton = +button(
                size = WidgetSize.WrapContent,
                content = ButtonWidget.Content.Text(Value.data("About".desc())),
                onTap = ::onAboutPressed
            )

            constraints {
                nameInput centerYToCenterY root
                nameInput leftRightToLeftRight root offset 16

                submitButton bottomToBottom root.safeArea offset 16
                submitButton leftRightToLeftRight root offset 16

                githubButton rightToRight root offset 16
                githubButton topToTop root.safeArea offset 16

                aboutButton rightToLeft githubButton offset 8
                aboutButton topToTop githubButton
            }
        }
    }

    private fun onGitHubPressed() {
        showPhonePicker(phonePickerHandler)
    }

    private fun onAboutPressed() {
        showDialog(
            dialogId = 2,
            title = "Question 2".desc(),
            message = "No or yes?".desc(),
            positiveButton = "No".desc(),
            negativeButton = "Yes".desc(),
            buttonsHandler = openUrlDialogHandler
        )
    }

    private val phonePickerHandler by registerPhonePickerHandler(9) {
        showToast("picked $it".desc())
    }

    object Ids {
        object Phone : InputWidget.Id
    }

    override fun routeInputCode(token: String) {
        routeInputCode.route(token)
    }

    override fun showError(error: StringDesc) {
        showToast(error)
    }

    private val openUrlDialogHandler by registerDialogButtonsHandler(
        onPositivePressed = {
            showToast("positive from $it".desc())
        },
        onNegativePressed = {
            showToast("negative from $it".desc())
        }
    )
}

class InputPhoneViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel(), EventsDispatcherOwner<InputPhoneViewModel.EventsListener> {

    val phoneField: FormField<String, StringDesc> = FormField(
        initialValue = "",
        validation = liveBlock { null }
    )

    fun onSubmitPressed() {
        val phone = phoneField.data.value
        if (phone.isBlank()) {
            eventsDispatcher.dispatchEvent { showError("it's cant be blank!".desc()) }
            return
        }
        val token = phone
        eventsDispatcher.dispatchEvent { routeInputCode(token) }
    }

    interface EventsListener {
        fun routeInputCode(token: String)
        fun showError(error: StringDesc)
    }
}