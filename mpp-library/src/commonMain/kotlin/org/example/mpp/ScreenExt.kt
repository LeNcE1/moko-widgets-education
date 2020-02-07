package org.example.mpp

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.widgets.screen.Screen

expect fun Screen<*>.openUrl(url: String)

expect fun Screen<*>.showMessage(title: StringDesc, message: StringDesc)