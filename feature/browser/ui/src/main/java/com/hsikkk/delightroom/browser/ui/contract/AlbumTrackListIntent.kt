package com.hsikkk.delightroom.browser.ui.contract

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

internal sealed interface AlbumTrackListIntent {

    @Stable
    object Initialize : AlbumTrackListIntent

    @Stable
    object GoBack : AlbumTrackListIntent

    @Stable
    object ClickPlayAlbumButton : AlbumTrackListIntent

    @Stable
    object ClickPlayAlbumRandomButton : AlbumTrackListIntent

    @Immutable
    data class ClickPlayTrack(
        val trackId: Long
    ) : AlbumTrackListIntent
}
