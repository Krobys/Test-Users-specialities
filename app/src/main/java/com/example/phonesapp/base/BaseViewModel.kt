package com.example.phonesapp.base

import androidx.lifecycle.AndroidViewModel
import com.example.phonesapp.PhoneListApp
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(application: PhoneListApp) : AndroidViewModel(application) {
    protected val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}