package com.hsikkk.delightroom.domain.model.valueobject

import com.hsikkk.delightroom.domain.model.entity.Track

data class MediaPlayerStatus(
    val isInPlaying: Boolean,

    val currentTrackIndex: Int,
    val playList: List<Track>,

    val currentPosition: Long,

    val volume: Float,

    val repeatMode: RepeatMode,
    val isShuffleEnabled: Boolean,
    val canGoNext: Boolean,
){
    val currentTrack: Track?
        get() = playList.getOrNull(currentTrackIndex)
    val duration: Long?
        get() = currentTrack?.duration
}

enum class RepeatMode{
    REPEAT_OFF,
    REPEAT_ALL,
    REPEAT_ONE,
}
