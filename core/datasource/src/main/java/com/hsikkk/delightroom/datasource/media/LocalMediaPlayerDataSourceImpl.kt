package com.hsikkk.delightroom.datasource.media

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.hsikkk.delightroom.data.datasource.LocalMediaPlayerDataSource
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerAction
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerStatus
import com.hsikkk.delightroom.mediaplayer.MedialPlayerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LocalMediaPlayerDataSourceImpl(
    private val context : Context
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
        CoroutineScope(Dispatchers.IO).launch {
            MedialPlayerService.observeMediaController {
                addPlayerEventListener()
            }
        }
    }

    override fun requestMediaPlayerAction(action: MediaPlayerAction) {
        runPlayerAction {
            when (action) {
                MediaPlayerAction.Prepare -> prepare()

                MediaPlayerAction.Play -> play()

                MediaPlayerAction.Pause -> pause()

                is MediaPlayerAction.AddItemsToPlaylist -> {
                    val newItems = if (action.shuffleRandom) {
                        action.items.shuffled()
                    } else action.items

                    addMediaItems(
                        newItems
                            .map { MediaItem.fromUri(Uri.parse(it.contentUri.toString())) }
                            .toList()
                    )

                    seekTo(action.startIndex, 0)

                    playerStatusFlow.value = playerStatusFlow.value.copy(
                        playList = newItems,
                        currentTrackIndex = action.startIndex,
                        progress = 0f,
                    )
                }

                MediaPlayerAction.GoNext -> seekToNext()

                MediaPlayerAction.GoPrev -> seekToPrevious()

                is MediaPlayerAction.SeekTo -> {
                    playerStatusFlow.value.duration?.let{
                        seekTo((it * action.progress).toLong())
                    }
                }

                is MediaPlayerAction.ChangeVolume -> volume = action.volume
            }
        }
    }

    override fun observeMediaPlayerStatus(): Flow<MediaPlayerStatus> {
        return playerStatusFlow
    }

    private fun runPlayerAction(action: MediaController.() -> Unit){
        MedialPlayerService.requestToPlayer(
            context, action
        )
    }

    private fun MediaController.addPlayerEventListener(){
        addListener(object : Player.Listener {

            //TODO : player stautus 변경 사항 emit

        })
    }
}
