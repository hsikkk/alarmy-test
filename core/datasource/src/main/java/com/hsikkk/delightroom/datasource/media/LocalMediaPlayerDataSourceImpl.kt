package com.hsikkk.delightroom.datasource.media

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.hsikkk.delightroom.data.datasource.LocalMediaPlayerDataSource
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerAction
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerStatus
import com.hsikkk.delightroom.mediaplayer.MedialPlayerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
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
            currentPosition = 0,
            volume = 0f,
        )
    )

    init {
        CoroutineScope(Dispatchers.Main).launch {
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

                is MediaPlayerAction.SetItemsToPlaylist -> {
                    val newItems = if (action.shuffleRandom) {
                        action.items.shuffled()
                    } else action.items

                    setMediaItems(
                        newItems
                            .map { MediaItem.fromUri(Uri.parse(it.contentUri.toString())) }
                            .toList()
                    )

                    prepare()

                    seekTo(action.startIndex, 0)

                    playerStatusFlow.value = playerStatusFlow.value.copy(
                        playList = newItems,
                        currentTrackIndex = action.startIndex,
                        currentPosition = 0,
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
        addListener(object : Player.Listener, AutoCloseable {
            private val handler = Handler(Looper.getMainLooper())
            private val delay: Long = 1000
            private val checkCurrentPositionRunnable = object : Runnable {
                override fun run() {
                    playerStatusFlow.value = playerStatusFlow.value.copy(
                        currentPosition = currentPosition
                    )

                    handler.postDelayed(this, delay)
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                playerStatusFlow.value = playerStatusFlow.value.copy(
                    isInPlaying = isPlaying
                )

                if(isPlaying){
                    handler.postDelayed(checkCurrentPositionRunnable, delay)
                } else {
                    handler.removeCallbacks(checkCurrentPositionRunnable)
                }
            }

            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {
                super.onPositionDiscontinuity(oldPosition, newPosition, reason)
                playerStatusFlow.value = playerStatusFlow.value.copy(
                    currentPosition = currentPosition
                )
            }

            override fun onVolumeChanged(volume: Float) {
                super.onVolumeChanged(volume)
                playerStatusFlow.value = playerStatusFlow.value.copy(
                    volume = volume
                )
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                playerStatusFlow.value = playerStatusFlow.value.copy(
                    currentTrackIndex = currentMediaItemIndex
                )
            }

            override fun close() {
                handler.removeCallbacks(checkCurrentPositionRunnable)
            }
        })
    }
}
