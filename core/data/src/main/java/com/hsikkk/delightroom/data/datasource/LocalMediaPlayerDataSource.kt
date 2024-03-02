package com.hsikkk.delightroom.data.datasource

import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerAction
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerStatus
import kotlinx.coroutines.flow.Flow

interface LocalMediaPlayerDataSource {
    fun requestMediaPlayerAction(action: MediaPlayerAction)

    fun observeMediaPlayerStatus(): Flow<MediaPlayerStatus>
}
