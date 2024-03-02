package com.hsikkk.delightroom.di

import com.hsikkk.delightroom.domain.repository.MediaPlayerRepository
import com.hsikkk.delightroom.domain.repository.MediaRepository
import com.hsikkk.delightroom.domain.usecase.media.GetAlbumTrackListUseCase
import com.hsikkk.delightroom.domain.usecase.media.GetAlbumsUseCase
import com.hsikkk.delightroom.domain.usecase.media.ObserveMediaPlayerStatusUseCase
import com.hsikkk.delightroom.domain.usecase.media.RequestMediaPlayerActionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideGetAlbumsUseCase(
        mediaRepository: MediaRepository
    ) : GetAlbumsUseCase {
        return GetAlbumsUseCase(
            mediaRepository = mediaRepository
        )
    }

    @Singleton
    @Provides
    fun provideGetAlbumTrackListUseCase(
        mediaRepository: MediaRepository
    ) : GetAlbumTrackListUseCase {
        return GetAlbumTrackListUseCase(
            mediaRepository = mediaRepository
        )
    }

    @Singleton
    @Provides
    fun provideRequestMediaPlayerActionUseCase(
        mediaPlayerRepository: MediaPlayerRepository
    ) : RequestMediaPlayerActionUseCase {
        return RequestMediaPlayerActionUseCase(
            mediaPlayerRepository = mediaPlayerRepository
        )
    }

    @Singleton
    @Provides
    fun provideObserveMediaPlayerStatusUseCase(
        mediaPlayerRepository: MediaPlayerRepository
    ) : ObserveMediaPlayerStatusUseCase {
        return ObserveMediaPlayerStatusUseCase(
            mediaPlayerRepository = mediaPlayerRepository
        )
    }
}
