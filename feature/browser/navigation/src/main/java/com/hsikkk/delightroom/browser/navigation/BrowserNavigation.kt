package com.hsikkk.delightroom.browser.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.hsikkk.delightroom.browser.ui.AlbumRoute
import com.hsikkk.delightroom.browser.ui.AlbumTrackListRoute
import com.hsikkk.delightroom.navigation.composable

fun NavGraphBuilder.browser(
    navController : NavController,
) {
    composable(BrowserDirections.albums){
        AlbumRoute(
            goBack = {}, //TODO
            goAlbumDetail = { albumId ->  navController.navigateToAlbumTrackList(albumId)}
        )
    }

    composable(BrowserDirections.albumTracks){
        AlbumTrackListRoute(
            goBack = { navController.popBackStack() },
        )
    }
}
