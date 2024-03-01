package com.hsikkk.delightroom.di

import com.hsikkk.delightroom.data.datasource.LocalMediaDataSource
import com.hsikkk.delightroom.data.repository.MediaRepositoryImpl
import com.hsikkk.delightroom.domain.repository.MediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMediaRepository(
        localMediaDataSource: LocalMediaDataSource
    ) : MediaRepository {
        return MediaRepositoryImpl(
            localMediaDataSource = localMediaDataSource
        )
    }
}
