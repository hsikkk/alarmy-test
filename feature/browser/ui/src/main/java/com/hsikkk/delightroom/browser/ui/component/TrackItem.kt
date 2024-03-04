package com.hsikkk.delightroom.browser.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hsikkk.delightroom.common.util.clickableSingle
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme
import com.hsikkk.delightroom.domain.model.entity.Track
import java.net.URI

@Composable
internal fun TrackItem(
    modifier: Modifier = Modifier,
    track: Track,
    onClickItem: (trackId: Long) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(Color.White)
            .clickableSingle { onClickItem(track.id) }
            .padding(horizontal = 20.dp, vertical = 12.dp),
    ) {
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = "${track.cdTrackNumber}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.LightGray,
            textAlign = TextAlign.Start,
            modifier = Modifier.width(20.dp),
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "${track.name}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

@Preview(name = "TrackItem")
@Composable
private fun TrackItemPreview(

) {
    DelightroomtestTheme {
        TrackItem(
            modifier = Modifier.fillMaxWidth(),
            track = Track(
                id = 1,
                name = "track1",
                duration = 141414,
                albumId = 1,
                albumName = "album1",
                artist = "artist1",
                cdTrackNumber = 2,
                albumArtUri = URI.create("test"),
                contentUri = URI.create("test"),
            ),
            onClickItem = {},
        )
    }
}
