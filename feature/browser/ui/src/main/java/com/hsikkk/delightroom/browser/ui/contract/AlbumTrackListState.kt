package com.hsikkk.delightroom.browser.ui.contract

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.hsikkk.delightroom.domain.model.entity.Track
import kotlinx.collections.immutable.ImmutableList

internal sealed interface AlbumTrackListState {
    @Stable
    object Loading: AlbumTrackListState

    @Immutable
    data class FetchSuccess(
        val albumName: String,
        val artist: String,
        val albumArtUri: String,
        val tracks: ImmutableList<Track>
    ) : AlbumTrackListState

    @Stable
    object NoPermission : AlbumTrackListState
    companion object {
        fun initialState(): AlbumTrackListState = Loading
    }
}
