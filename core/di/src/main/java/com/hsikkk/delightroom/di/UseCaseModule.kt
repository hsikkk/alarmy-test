package com.hsikkk.delightroom.di

import com.hsikkk.delightroom.domain.repository.MediaRepository
import com.hsikkk.delightroom.domain.usecase.media.GetAlbumTrackListUseCase
import com.hsikkk.delightroom.domain.usecase.media.GetAlbumsUseCase
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
}
