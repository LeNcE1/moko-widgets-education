package org.example.mpp.friends

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.ImageWidget
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.image
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.text
import dev.icerock.moko.widgets.units.UnitItemRoot
import dev.icerock.moko.widgets.units.WidgetsTableUnitItem

class FriendUnitItem(
    private val theme: Theme,
    itemId: Long,
    friend: Friend
) : WidgetsTableUnitItem<Friend>(
    itemId = itemId,
    data = friend
) {
    override val reuseId: String = "friendCell"

    override fun createWidget(data: LiveData<Friend>): UnitItemRoot {
        return with(theme) {
            constraint(
                size = WidgetSize.WidthAsParentHeightWrapContent
            ) {
                val title = +text(
                    size = WidgetSize.Const(
                        width = SizeSpec.MatchConstraint,
                        height = SizeSpec.WrapContent
                    ),
                    text = data.map { it.name.desc() as StringDesc }
                )

                val avatar = +image(
                    size = WidgetSize.Const(
                        width = SizeSpec.Exact(64f),
                        height = SizeSpec.Exact(64f)
                    ),
                    image = data.map { Image.network(it.avatarUrl) },
                    scaleType = ImageWidget.ScaleType.FIT
                )

                constraints {
                    avatar.top pin root.top offset 16
                    avatar.left pin root.left offset 16
                    avatar.bottom pin root.bottom offset 16

                    title.left pin avatar.right offset 8
                    title.right pin root.right offset 16
                    title centerYToCenterY root
                }
            }
        }.let { UnitItemRoot.from(it) }
    }
}