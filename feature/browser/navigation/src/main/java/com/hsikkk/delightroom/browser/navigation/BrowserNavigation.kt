package com.hsikkk.delightroom.browser.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.hsikkk.delightroom.browser.ui.AlbumRoute
import com.hsikkk.delightroom.common.navigation.composable

fun NavGraphBuilder.browser(
    navController : NavController,
) {
    composable(BrowserDirections.albums){
        AlbumRoute(
            goBack = {}, //TODO
            goAlbumDetail = {} //TODO
        )
    }
}
