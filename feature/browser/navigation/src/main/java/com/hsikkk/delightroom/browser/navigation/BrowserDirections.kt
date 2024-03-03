package com.hsikkk.delightroom.browser.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.hsikkk.delightroom.browser.ui.KEY_ALBUM_ID
import com.hsikkk.delightroom.common.navigation.NavigationCommand
import com.hsikkk.delightroom.common.navigation.putParameter

object BrowserDirections {
    val albums = object : NavigationCommand {
        override val queryArguments: List<NamedNavArgument> = emptyList()
        override val path: String = "albums"
    }

    val albumTracks = object : NavigationCommand {
        override val queryArguments: List<NamedNavArgument> = listOf(
            navArgument(name = KEY_ALBUM_ID){
                type = NavType.LongType
            }
        )
        override val path: String = "album/tracks"
    }
}

fun NavController.navigateToAlbumList() {
    this.navigate(BrowserDirections.albums.destination)
}

fun NavController.navigateToAlbumTrackList(albumId: Long) {
    this.navigate(
        BrowserDirections.albumTracks.destination
            .putParameter(KEY_ALBUM_ID, albumId.toString())
    )
}

