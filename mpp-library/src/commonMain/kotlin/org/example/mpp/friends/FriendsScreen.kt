package org.example.mpp.friends

import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.units.TableUnitItem
import dev.icerock.moko.widgets.ListWidget
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Widget
import dev.icerock.moko.widgets.list
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize

class FriendsScreen(
    private val theme: Theme
) : WidgetScreen<Args.Empty>(), NavigationItem {

    override val navigationBar: NavigationBar = NavigationBar.Normal(title = "Friends".desc())

    override fun createContentWidget(): Widget<WidgetSize.Const<SizeSpec.AsParent, SizeSpec.AsParent>> {
        val viewModel = getViewModel { FriendsViewModel() }

        return with(theme) {
            list(
                size = WidgetSize.AsParent,
                id = Ids.List,
                items = viewModel.friends.map { friendsToTableUnits(it) }
            )
        }
    }

    private fun Theme.friendsToTableUnits(friends: List<Friend>): List<TableUnitItem> {
        return friends.map { friend ->
            FriendUnitItem(
                theme = theme,
                itemId = friend.id.toLong(),
                friend = friend
            )
        }
    }

    object Ids {
        object List : ListWidget.Id
    }
}