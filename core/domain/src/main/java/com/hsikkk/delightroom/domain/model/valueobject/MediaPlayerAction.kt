package com.hsikkk.delightroom.domain.model.valueobject

import com.hsikkk.delightroom.domain.model.entity.Track

sealed interface MediaPlayerAction {
    object Prepare : MediaPlayerAction

    object Play : MediaPlayerAction

    object Pause : MediaPlayerAction

    data class SetItemsToPlaylist(
        val items: List<Track>,
        val shuffleRandom: Boolean,
        val startIndex: Int,
    ) : MediaPlayerAction

    object GoNext : MediaPlayerAction

    object GoPrev : MediaPlayerAction

    data class SeekTo(
        val position: Long
    ) : MediaPlayerAction

    data class ChangeVolume(
        val volume: Float
    ): MediaPlayerAction

    data class ChangeRepeatMode(
        val repeatMode: RepeatMode
    ): MediaPlayerAction

    data class SetShuffleEnabled(
        val enabled: Boolean
    ): MediaPlayerAction
}
