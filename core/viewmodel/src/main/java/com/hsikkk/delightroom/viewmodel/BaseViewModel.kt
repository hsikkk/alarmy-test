package com.hsikkk.delightroom.viewmodel

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost

abstract class BaseViewModel<Intent: Any, STATE : Any, SIDE_EFFECT : Any>
    : ViewModel(), ContainerHost<STATE, SIDE_EFFECT> {

    // View에서는 onIntent를 통해서만 이벤트 전달
    fun onIntent(intent: Intent){
        handleIntent(intent)
    }

    protected abstract fun handleIntent(intent: Intent)
}
