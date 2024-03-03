package com.hsikkk.delightroom.browser.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hsikkk.delightroom.common.util.clickableSingle
import com.hsikkk.delightroom.designsystem.theme.DelightroomtestTheme
import com.hsikkk.delightroom.domain.model.entity.Album
import java.net.URI

@Composable
internal fun AlbumItem(
    modifier: Modifier = Modifier,
    album: Album,
    onClickItem: (albumId : Long) -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickableSingle { onClickItem(album.id) }
            .shadow(1.dp, clip = true, shape = RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.Start,
        ) {
            AsyncImage(
                model = album.albumArtUri.toString(),
                contentDescription = album.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = album.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = album.artist,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.LightGray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(name = "loading placeholder")
@Composable
private fun AlbumItemPreview(

) {
    DelightroomtestTheme {
        AlbumItem(
            modifier = Modifier.width(200.dp),
            album = Album(
                id = 1,
                name = "album1",
                artist = "artist1",
                albumArtUri = URI.create("testUri"),
            ),
            onClickItem = {},
        )
    }
}
