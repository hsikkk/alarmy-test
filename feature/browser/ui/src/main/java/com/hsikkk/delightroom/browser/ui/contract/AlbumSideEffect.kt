package com.hsikkk.delightroom.browser.ui.contract

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

internal sealed interface AlbumSideEffect {
    @Stable
    object GoBack : AlbumSideEffect

    @Immutable
    data class GoAlbumDetail(
        val albumId: Long
    ) : AlbumSideEffect
}
