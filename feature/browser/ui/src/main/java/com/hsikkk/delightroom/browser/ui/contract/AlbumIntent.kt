package com.hsikkk.delightroom.browser.ui.contract

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

internal sealed interface AlbumIntent {
    @Stable
    object Initialize : AlbumIntent

    @Stable
    object GoBack : AlbumIntent

    @Immutable
    data class GoAlbumDetail(
        val albumId: Long
    ) : AlbumIntent
}
