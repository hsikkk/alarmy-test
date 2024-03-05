package com.hsikkk.delightroom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.composable(
    command: NavigationCommand,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = command.destination,
        arguments = command.queryArguments,
        content = content,
    )
}

fun String.putParameter(
    key: String,
    value: String,
) = this.replace("{$key}", value)
