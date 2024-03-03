package com.hsikkk.delightroom.browser.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hsikkk.delightroom.browser.ui.AlbumRoute

fun NavGraphBuilder.browser(
    navController : NavController,
) {
    composable("/"){
        AlbumRoute(
            goBack = {},
            goAlbumDetail = {}
        )
    }
}
