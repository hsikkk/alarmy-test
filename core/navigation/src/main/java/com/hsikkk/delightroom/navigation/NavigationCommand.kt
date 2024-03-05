package com.hsikkk.delightroom.navigation

import androidx.navigation.NamedNavArgument

interface NavigationCommand {

    val queryArguments: List<NamedNavArgument>
    val path: String

    private val query: String
        get() = queryArguments.map { it.name }
            .toQueryString()

    val destination: String
        get() = "$path$query"

    private fun List<String>.toQueryString() =
        if (this.isEmpty()) "" else joinToString(separator = "&", prefix = "?") { "$it={$it}" }

}
