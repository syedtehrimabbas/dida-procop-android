package com.androidstarter.base.viewmodel


import android.content.Context
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.androidstarter.base.clickevents.SingleClickEvent
import com.androidstarter.base.interfaces.IBase
import com.androidstarter.base.interfaces.OnClickHandler
import com.androidstarter.base.state.UIState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

abstract class HiltBaseViewModel<VS : IBase.State> : BaseCoroutineViewModel(),
    IBase.ViewModel<VS>, OnClickHandler {
    @CallSuper
    override fun onCleared() {
        cancelAllJobs()
        super.onCleared()
    }

    override fun cancelAllJobs() {
        viewModelBGScope.close()
        viewModelScope.cancel()
        viewModelJob.cancel()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        viewState.onCreate()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
        viewState.onStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
        viewState.resume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
        viewState.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {
        viewState.onStop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        viewState.destroy()
    }

    override fun registerLifecycleOwner(owner: LifecycleOwner?) {
        unregisterLifecycleOwner(owner)
        owner?.lifecycle?.addObserver(this)
    }

    override fun unregisterLifecycleOwner(owner: LifecycleOwner?) {
        owner?.lifecycle?.removeObserver(this)
    }

    override fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    fun getString(keyID: Int, appContext: Context): String =
        appContext.getString(keyID)


    override val clickEvent: SingleClickEvent? = SingleClickEvent()

    /**
     * override this method when there is  no need to use its super implementation.
     * recommended to not override this method. use @see <handleOnClick> must override
     */
    override fun handlePressOnView(id: Int) {
        clickEvent?.setValue(id)
        handleOnClick(id)
    }

    /**
     * Override this method in your [ViewModel]
     * you can manage your owen onclick logic by overriding this method
     */
    open fun handleOnClick(id: Int) {}

    override fun loading(isLoading: Boolean, message: String) {
        viewState.uiState.postValue(UIState.Loading(isLoading, message))
    }

    override fun alert(message: String) {
        viewState.uiState.postValue(UIState.Alert(message))
    }
}


