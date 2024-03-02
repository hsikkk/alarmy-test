package com.hsikkk.delightroom.di

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaPlayerModule {
    @Singleton
    @Provides
    fun providePlayer(
        @ApplicationContext context: Context
    ) : Player {
        return ExoPlayer.Builder(context).build()
    }
}
