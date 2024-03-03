package com.hsikkk.delightroom.browser.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import com.hsikkk.delightroom.common.navigation.NavigationCommand

object BrowserDirections {
    val albums = object : NavigationCommand {
        override val queryArguments: List<NamedNavArgument> = emptyList()
        override val path: String = "/"
    }
}

fun NavController.navigateToAlbumList() {
    this.navigate(BrowserDirections.albums.destination)
}
