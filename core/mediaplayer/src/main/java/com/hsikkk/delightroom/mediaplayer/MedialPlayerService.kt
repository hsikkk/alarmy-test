package com.hsikkk.delightroom.mediaplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect

class MedialPlayerService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession?.player
        if (player?.playWhenReady == true) {
            player.pause()
        }
        stopSelf()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    companion object {
        private var mediaController =  MutableStateFlow<MediaController?>(null)
        fun requestToPlayer(context: Context, action: MediaController.() -> Unit) {
            if (mediaController.value != null) {
                mediaController.value!!.action()
            } else {
                val sessionToken =
                    SessionToken(context, ComponentName(context, MedialPlayerService::class.java))
                val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()

                controllerFuture.addListener(
                    {
                        mediaController.value?.release()
                        controllerFuture.get().apply {
                            mediaController.value = this
                            action()
                        }
                    },
                    MoreExecutors.directExecutor()
                )
            }

        }

        suspend fun observeMediaController(action: MediaController.() -> Unit){
            mediaController.collect {
                it?.action()
            }
        }
    }
}
