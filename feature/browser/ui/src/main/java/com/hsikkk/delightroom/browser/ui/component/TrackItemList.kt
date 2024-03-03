package com.hsikkk.delightroom.browser.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme
import com.hsikkk.delightroom.domain.model.entity.Track
import java.net.URI

@Composable
internal fun TrackItemList(
    modifier: Modifier = Modifier,
    tracks: List<Track>,
    onClickItem: (trackId: Long) -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .shadow(1.dp, clip = true, shape = RoundedCornerShape(12.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
        ) {
            tracks.forEachIndexed { index, track ->
                TrackItem(
                    modifier = Modifier.fillMaxWidth(),
                    track = track,
                    onClickItem = onClickItem,
                )

                if (index < tracks.lastIndex) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }
    }
}

@Preview(name = "track item 목록")
@Composable
private fun TrackItemListPreview(

) {
    DelightroomtestTheme {
        TrackItemList(
            modifier = Modifier.fillMaxWidth(),
            tracks = (0..15).map {
                Track(
                    id = it.toLong(),
                    name = "track$it",
                    duration = 141414,
                    albumId = it.toLong(),
                    albumName = "album$it",
                    artist = "artist$it",
                    cdTrackNumber = it + 1,
                    albumArtUri = URI.create("test"),
                    contentUri = URI.create("test"),
                )
            },
            onClickItem = {},
        )
    }
}
