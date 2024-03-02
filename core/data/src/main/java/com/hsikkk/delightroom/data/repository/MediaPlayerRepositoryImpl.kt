package com.hsikkk.delightroom.data.repository

import com.hsikkk.delightroom.data.datasource.LocalMediaPlayerDataSource
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerAction
import com.hsikkk.delightroom.domain.model.valueobject.MediaPlayerStatus
import com.hsikkk.delightroom.domain.repository.MediaPlayerRepository
import kotlinx.coroutines.flow.Flow

class MediaPlayerRepositoryImpl(
    private val localMediaPlayerDataSource: LocalMediaPlayerDataSource,
) : MediaPlayerRepository{
    override fun requestMediaPlayerAction(action: MediaPlayerAction)
        = localMediaPlayerDataSource.requestMediaPlayerAction(action)

    override fun observeMediaPlayerStatus(): Flow<MediaPlayerStatus>
        = localMediaPlayerDataSource.observeMediaPlayerStatus()
}
