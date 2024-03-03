package com.hsikkk.delightroom.browser.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hsikkk.delightroom.browser.ui.contract.AlbumTrackListState
import com.hsikkk.delightroom.domain.model.entity.Track
import kotlinx.collections.immutable.toPersistentList
import java.net.URI

internal class AlbumTrackListScreenPreviewProvider :
    PreviewParameterProvider<AlbumTrackListState> {
    override val values: Sequence<AlbumTrackListState> = sequenceOf(
        AlbumTrackListState.Loading,
        AlbumTrackListState.NoPermission,
        AlbumTrackListState.FetchSuccess(
            albumName = "album",
            artist = "artist",
            albumArtUri = "",
            tracks = (0..10).map {
                Track(
                    id = it.toLong(),
                    name = "track$it",
                    duration = 141414,
                    albumId = 1,
                    albumName = "album",
                    artist = "artist",
                    cdTrackNumber = it + 1,
                    albumArtUri = URI.create("test"),
                    contentUri = URI.create("test"),
                )
            }.toPersistentList()
        ),
    )
}
