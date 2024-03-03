package com.hsikkk.delightroom.test

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hsikkk.delightroom.browser.navigation.BrowserDirections
import com.hsikkk.delightroom.browser.navigation.browser

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = BrowserDirections.albums.destination) {
        browser(navController)
    }

}
