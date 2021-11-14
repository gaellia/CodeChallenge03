package com.launchpad.codechallenge03.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified VM: ViewModel?> getViewModelFactory(
    crossinline getViewModel: () -> VM
): ViewModelProvider.Factory{
    return object: ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(VM::class.java)){
                return getViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}