package com.hsikkk.delightroom.browser.ui.contract

import androidx.compose.runtime.Stable

internal sealed interface AlbumTrackListSideEffect {
    @Stable
    object GoBack : AlbumTrackListSideEffect
}
