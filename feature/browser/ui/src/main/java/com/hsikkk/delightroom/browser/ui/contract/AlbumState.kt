package com.hsikkk.delightroom.browser.ui.contract

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.hsikkk.delightroom.domain.model.entity.Album
import kotlinx.collections.immutable.ImmutableList

internal sealed interface AlbumState {

    @Stable
    object Loading: AlbumState

    @Immutable
    data class FetchSuccess(
        val albums: ImmutableList<Album>
    ) : AlbumState

    @Stable
    object NoPermission : AlbumState

    companion object {
        fun initialState(): AlbumState = Loading
    }
}
