package org.example.mpp

import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.BaseApplication
import dev.icerock.moko.widgets.screen.ScreenDesc
import dev.icerock.moko.widgets.screen.TypedScreenDesc
import dev.icerock.moko.widgets.screen.navigation.BottomNavigationItem
import dev.icerock.moko.widgets.screen.navigation.BottomNavigationScreen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.NavigationScreen
import dev.icerock.moko.widgets.screen.navigation.createPushResultRoute
import dev.icerock.moko.widgets.screen.navigation.createPushRoute
import dev.icerock.moko.widgets.screen.navigation.createReplaceRoute
import dev.icerock.moko.widgets.screen.navigation.createRouter
import org.example.mpp.auth.AuthFactory
import org.example.mpp.auth.InputCodeScreen
import org.example.mpp.auth.InputPhoneScreen
import org.example.mpp.info.InfoScreen
import org.example.mpp.profile.EditProfileScreen
import org.example.mpp.profile.ProfileFactory
import org.example.mpp.profile.ProfileScreen

class App : BaseApplication() {
    override fun setup(): ScreenDesc<Args.Empty> {
        val theme = Theme()

        val authFactory = AuthFactory(theme)
        val profileFactory = ProfileFactory(theme)

        return registerScreen(RootNavigationScreen::class) {
            val rootNavigationRouter = createRouter()

            val mainScreen = registerScreen(MainBottomNavigationScreen::class) {
                val bottomNavigationRouter = createRouter()

                val profileNavigationScreen = registerProfileTab(
                    profileFactory = profileFactory,
                    rootNavigationRouter = rootNavigationRouter
                )

                val infoScreen = registerScreen(InfoScreen::class) {
                    InfoScreen(theme = theme, routeProfile = bottomNavigationRouter.createChangeTabRoute(2))
                }

                MainBottomNavigationScreen(
                    router = bottomNavigationRouter
                ) {
                    tab(
                        id = 1,
                        title = "Info".desc(),
                        icon = null,
                        screenDesc = infoScreen
                    )

                    tab(
                        id = 2,
                        title = "Profile".desc(),
                        icon = null,
                        screenDesc = profileNavigationScreen
                    )
                }
            }

            val inputCodeScreen = registerScreen(InputCodeScreen::class) {
                authFactory.createInputCodeScreen(
                    routeMain = rootNavigationRouter.createReplaceRoute(mainScreen)
                )
            }

            val inputPhoneScreen = registerScreen(InputPhoneScreen::class) {
                authFactory.createInputPhoneScreen(
                    routeInputCode = rootNavigationRouter.createPushRoute(inputCodeScreen) {
                        InputCodeScreen.Arg(it)
                    }
                )
            }

            RootNavigationScreen(
                initialScreen = inputPhoneScreen,
                router = rootNavigationRouter
            )
        }
    }

    private fun registerProfileTab(
        profileFactory: ProfileFactory,
        rootNavigationRouter: NavigationScreen.Router
    ): TypedScreenDesc<Args.Empty, ProfileNavigationScreen> {
        return registerScreen(ProfileNavigationScreen::class) {
            val navigationRouter = createRouter()

            val profileEditScreen = registerScreen(EditProfileScreen::class) {
                profileFactory.createEditProfileScreen(
                    routeBack = navigationRouter.createPopRoute()
                )
            }

            val profileScreen = registerScreen(ProfileScreen::class) {
                profileFactory.createProfileScreen(
                    routeEdit = navigationRouter.createPushResultRoute(profileEditScreen) { it.edited },
                    routeLogout = rootNavigationRouter.createPopRoute()
                )
            }

            ProfileNavigationScreen(
                initialScreen = profileScreen,
                router = navigationRouter
            )
        }
    }
}

class RootNavigationScreen(
    initialScreen: TypedScreenDesc<Args.Empty, InputPhoneScreen>,
    router: Router
) : NavigationScreen<InputPhoneScreen>(initialScreen, router)

class MainBottomNavigationScreen(
    router: Router,
    builder: BottomNavigationItem.Builder.() -> Unit
) : BottomNavigationScreen(router, builder), NavigationItem {
    override val navigationBar: NavigationBar = NavigationBar.None
}

class ProfileNavigationScreen(
    initialScreen: TypedScreenDesc<Args.Empty, ProfileScreen>,
    router: Router
) : NavigationScreen<ProfileScreen>(initialScreen, router)