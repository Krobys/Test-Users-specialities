package com.example.phonesapp.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel, B: ViewDataBinding> : BaseActivityWithoutViewModel<B>() {
    protected abstract val viewModelClass: Class<VM>

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel: VM by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }

}