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
        val progress: Float
    ) : MediaPlayerAction

    data class ChangeVolume(
        val volume: Float
    ): MediaPlayerAction
}
