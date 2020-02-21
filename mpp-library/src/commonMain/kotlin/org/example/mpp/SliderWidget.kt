package org.example.mpp

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.ViewBundle
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.core.RequireId
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.core.Widget
import dev.icerock.moko.widgets.core.WidgetDef
import dev.icerock.moko.widgets.style.view.WidgetSize

@WidgetDef(SliderViewFactory::class)
class SliderWidget<WS : WidgetSize>(
    override val size: WS,
    private val factory: ViewFactory<SliderWidget<out WidgetSize>>,
    override val id: Id,
    val minValue: Int,
    val maxValue: Int,
    val value: MutableLiveData<Int>
) : Widget<WS>(), RequireId<SliderWidget.Id> {
    override fun buildView(viewFactoryContext: ViewFactoryContext): ViewBundle<WS> {
        return factory.build(this, size, viewFactoryContext)
    }

    interface Id : Theme.Id<SliderWidget<out WidgetSize>>

    interface Category : Theme.Category<SliderWidget<out WidgetSize>>

    object DefaultCategory : Category
}