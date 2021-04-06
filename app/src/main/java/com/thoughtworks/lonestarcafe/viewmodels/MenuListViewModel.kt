package com.thoughtworks.lonestarcafe.viewmodels

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thoughtworks.lonestarcafe.extensions.notifyObservers

class MenuListViewModel : ViewModel() {
    private val _selectedMenuItems: MutableLiveData<MutableList<String>> by lazy {
        MutableLiveData(mutableListOf())
    }
    val selectedMenuItems: LiveData<MutableList<String>>
        get() = _selectedMenuItems
    val isLoadingMenuItems: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener { view: CompoundButton, isChecked: Boolean ->
        val menuItemId = view.tag as String

        _selectedMenuItems.value?.apply {
            if (isChecked) {
                add(menuItemId)
            } else {
                remove(menuItemId)
            }
        }
        _selectedMenuItems.notifyObservers()
    }
}