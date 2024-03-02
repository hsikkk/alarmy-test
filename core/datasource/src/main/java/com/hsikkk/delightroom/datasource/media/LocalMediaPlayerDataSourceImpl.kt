package com.hsikkk.delightroom.datasource.media

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.hsikkk.delightroom.data.datasource.LocalMediaPlayerDataSource
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerAction
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class LocalMediaPlayerDataSourceImpl(
    private val player: Player
) : LocalMediaPlayerDataSource {

    private val playerStatusFlow = MutableStateFlow(
        MediaPlayerStatus(
            isInPlaying = false,
            currentTrackIndex = -1,
            playList = emptyList(),
            progress = 0f,
            volume = 0f,
        )
    )

    init {
        addPlayerEventListener()
    }

    override fun requestMediaPlayerAction(action: MediaPlayerAction) {
        when (action) {
            MediaPlayerAction.Prepare -> player.prepare()

            MediaPlayerAction.Play -> player.play()

            MediaPlayerAction.Pause -> player.pause()

            is MediaPlayerAction.AddItemsToPlaylist -> {
                val newItems = if (action.shuffleRandom) {
                    action.items.shuffled()
                } else action.items

                player.addMediaItems(
                    newItems
                        .map { MediaItem.fromUri(Uri.parse(it.contentUri.toString())) }
                        .toList()
                )

                player.seekTo(action.startIndex, 0)

                playerStatusFlow.value = playerStatusFlow.value.copy(
                    playList = newItems,
                    currentTrackIndex = action.startIndex,
                    progress = 0f,
                )
            }

            MediaPlayerAction.GoNext -> player.seekToNext()

            MediaPlayerAction.GoPrev -> player.seekToPrevious()

            is MediaPlayerAction.SeekTo -> {
                playerStatusFlow.value.duration?.let{
                    player.seekTo((it * action.progress).toLong())
                }
            }

            is MediaPlayerAction.ChangeVolume -> player.volume = action.volume
        }
    }

    override fun observeMediaPlayerStatus(): Flow<MediaPlayerStatus> {
        return playerStatusFlow
    }

    private fun addPlayerEventListener(){
        player.addListener(object : Player.Listener {
            //TODO : player stautus 변경 사항 emit
        })
    }
}
