package com.hsikkk.delightroom.di

import android.content.Context
import androidx.media3.common.Player
import com.hsikkk.delightroom.data.datasource.LocalMediaDataSource
import com.hsikkk.delightroom.data.datasource.LocalMediaPlayerDataSource
import com.hsikkk.delightroom.datasource.media.LocalMediaDataSourceImpl
import com.hsikkk.delightroom.datasource.media.LocalMediaPlayerDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideLocalMediaDataSource(
        @ApplicationContext context: Context
    ) : LocalMediaDataSource {
        return LocalMediaDataSourceImpl(
            context = context
        )
    }

    @Singleton
    @Provides
    fun provideLocalMediaPlayerDataSource(
        player: Player
    ) : LocalMediaPlayerDataSource {
        return LocalMediaPlayerDataSourceImpl(
            player = player
        )
    }
}
