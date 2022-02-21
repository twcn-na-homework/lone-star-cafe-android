package com.thoughtworks.lonestarcafe.viewmodels

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.thoughtworks.lonestarcafe.DiscountsQuery
import com.thoughtworks.lonestarcafe.MenuListQuery
import com.thoughtworks.lonestarcafe.extensions.notifyObservers

class MainViewModel(private val apolloClient: ApolloClient) : ViewModel() {
    private val _menuList: MutableLiveData<List<MenuListQuery.Menu>> by lazy {
        MutableLiveData()
    }
    private val _discounts: MutableLiveData<List<DiscountsQuery.Discount>?> by lazy {
        MutableLiveData()
    }
    private val _isLoadingDiscount: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    private val _selectedMenuItems: MutableLiveData<MutableList<String>> by lazy {
        MutableLiveData(mutableListOf())
    }

    val menuList: LiveData<List<MenuListQuery.Menu>>
        get() = _menuList

    val discounts: LiveData<List<DiscountsQuery.Discount>?>
        get() = _discounts

    val isLoadingDiscount: LiveData<Boolean>
        get() = _isLoadingDiscount

    val selectedMenuItems: LiveData<MutableList<String>>
        get() = _selectedMenuItems

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

    suspend fun loadMenuList() {
        val menuList = apolloClient.query(MenuListQuery()).await()

        _menuList.postValue(menuList.data?.menu)
    }

    suspend fun loadDiscount() {
        _isLoadingDiscount.value = true

        val discountsData = apolloClient.query(DiscountsQuery()).await()

        _isLoadingDiscount.value = false
        _discounts.postValue(discountsData.data?.discount)
    }
}

class MainViewModelFactory(private val apolloClient: ApolloClient): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(apolloClient) as T
}