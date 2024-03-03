package com.hsikkk.delightroom.browser.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hsikkk.delightroom.browser.ui.contract.AlbumState
import com.hsikkk.delightroom.domain.model.entity.Album
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import java.net.URI

internal class AlbumScreenPreviewProvider :
    PreviewParameterProvider<AlbumState> {
    override val values: Sequence<AlbumState> = sequenceOf(
        AlbumState.Loading,
        AlbumState.NoPermission,
        AlbumState.FetchSuccess(
          albums = (0..20).map {
              Album(
                  id = it.toLong(),
                  name = "album$it",
                  artist = "artist$it",
                  albumArtUri = URI.create("testUri"),
              )
          }.toPersistentList()
        ),
        AlbumState.FetchSuccess(
            albums = persistentListOf()
        ),
    )
}
