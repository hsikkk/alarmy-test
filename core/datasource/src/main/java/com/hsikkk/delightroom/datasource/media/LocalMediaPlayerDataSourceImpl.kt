package com.hsikkk.delightroom.datasource.media

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.common.Player.REPEAT_MODE_OFF
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.session.MediaController
import com.hsikkk.delightroom.data.datasource.LocalMediaPlayerDataSource
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerAction
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerStatus
import com.hsikkk.delightroom.domain.model.valueobject.RepeatMode
import com.hsikkk.delightroom.mediaplayer.MedialPlayerService
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

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
            repeatMode = RepeatMode.REPEAT_OFF,
            isShuffleEnabled = false,
        )
    )

    init {
        runPlayerAction {
            addPlayerEventListener()
            playerStatusFlow.value = playerStatusFlow.value.copy(
                repeatMode = this.repeatMode.toRepeatMode(),
                volume = this.volume,
                isShuffleEnabled = this.shuffleModeEnabled,
            )
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
                    seekTo(action.position)
                }

                is MediaPlayerAction.ChangeVolume -> volume = action.volume

                is MediaPlayerAction.ChangeRepeatMode -> {
                    repeatMode = when(action.repeatMode){
                        RepeatMode.REPEAT_OFF -> REPEAT_MODE_OFF
                        RepeatMode.REPEAT_ALL -> REPEAT_MODE_ALL
                        RepeatMode.REPEAT_ONE -> REPEAT_MODE_ONE
                    }
                }

                is MediaPlayerAction.SetShuffleEnabled -> shuffleModeEnabled = action.enabled
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

            override fun onRepeatModeChanged(repeatMode: Int) {
                super.onRepeatModeChanged(repeatMode)
                playerStatusFlow.value = playerStatusFlow.value.copy(
                    repeatMode = repeatMode.toRepeatMode()
                )
            }


            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                super.onShuffleModeEnabledChanged(shuffleModeEnabled)
                playerStatusFlow.value = playerStatusFlow.value.copy(
                    isShuffleEnabled = shuffleModeEnabled
                )
            }

            override fun close() {
                handler.removeCallbacks(checkCurrentPositionRunnable)
            }
        })
    }

    private fun Int.toRepeatMode() = when(this){
        REPEAT_MODE_OFF -> RepeatMode.REPEAT_OFF
        REPEAT_MODE_ALL -> RepeatMode.REPEAT_ALL
        REPEAT_MODE_ONE -> RepeatMode.REPEAT_ONE
        else -> RepeatMode.REPEAT_OFF // Not valid
    }
}
