package com.thoughtworks.lonestarcafe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuListViewModel : ViewModel() {
    val isLoadingMenuItems: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }
}