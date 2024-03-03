package com.hsikkk.delightroom.browser.ui

import com.hsikkk.delightroom.browser.ui.contract.AlbumIntent
import com.hsikkk.delightroom.browser.ui.contract.AlbumSideEffect
import com.hsikkk.delightroom.browser.ui.contract.AlbumState
import com.hsikkk.delightroom.common.viewmodel.BaseViewModel
import com.hsikkk.delightroom.domain.model.exception.NoPermissionException
import com.hsikkk.delightroom.domain.usecase.media.GetAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
internal class AlbumViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase
) : BaseViewModel<AlbumIntent, AlbumState, AlbumSideEffect>() {
    override val container: Container<AlbumState, AlbumSideEffect> = container(AlbumState.initialState())

    override fun handleIntent(intent: AlbumIntent) {
        when (intent) {
            AlbumIntent.Initialize -> initialize()

            is AlbumIntent.GoAlbumDetail -> goAlbumDetail(
                albumId = intent.albumId
            )

            AlbumIntent.GoBack -> onGoBack()
        }
    }

    private fun initialize() = intent {
        try {
            getAlbumsUseCase().let {
                reduce {
                    AlbumState.FetchSuccess(
                        albums = it.toImmutableList()
                    )
                }
            }
        } catch (e: NoPermissionException){
            reduce {
                AlbumState.NoPermission
            }
        }
    }

    private fun goAlbumDetail(albumId: Long) = intent {
        postSideEffect(AlbumSideEffect.GoAlbumDetail(albumId = albumId))
    }

    private fun onGoBack() = intent{
        postSideEffect(AlbumSideEffect.GoBack)
    }
}
