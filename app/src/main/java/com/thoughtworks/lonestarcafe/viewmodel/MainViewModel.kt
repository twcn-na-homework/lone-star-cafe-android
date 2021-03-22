package com.thoughtworks.lonestarcafe.viewmodel

import android.util.Log
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.thoughtworks.lonestarcafe.DiscountsQuery
import com.thoughtworks.lonestarcafe.MenuListQuery
import com.thoughtworks.lonestarcafe.extension.notifyObservers
import kotlinx.coroutines.launch

class MainViewModel(private val apolloClient: ApolloClient) : ViewModel() {
    private val _menuList: MutableLiveData<List<MenuListQuery.Menu>> by lazy {
        MutableLiveData()
    }
    private val _selectItems: MutableLiveData<MutableMap<String, MenuListQuery.Menu>> by lazy {
        MutableLiveData<MutableMap<String, MenuListQuery.Menu>>(HashMap())
    }
    private val _discounts: MutableLiveData<List<DiscountsQuery.Discount>?> by lazy {
        MutableLiveData()
    }
    private val _isLoadingDiscount: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val menuList: LiveData<List<MenuListQuery.Menu>>
        get() = _menuList

    val selectItems: LiveData<MutableMap<String, MenuListQuery.Menu>>
        get() = _selectItems

    val discounts: LiveData<List<DiscountsQuery.Discount>?>
        get() = _discounts

    val isLoadingDiscount: LiveData<Boolean>
        get() = _isLoadingDiscount

    val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener { view: CompoundButton, isChecked: Boolean ->
        val menuItem = view.tag as MenuListQuery.Menu
        _selectItems.value?.size

        _selectItems.value?.apply {
            if (isChecked) {
                put(menuItem.id, menuItem)
            } else {
                remove(menuItem.id)
            }
        }
        _selectItems.notifyObservers()
    }

    init {
        loadMenuList()
    }

    private fun loadMenuList() {
        viewModelScope.launch {
            apolloClient.query(MenuListQuery()).enqueue(object: ApolloCall.Callback<MenuListQuery.Data>() {
                override fun onResponse(response: Response<MenuListQuery.Data>) {
                    _menuList.postValue(response.data?.menu)
                }

                override fun onFailure(e: ApolloException) {
                    Log.e(MainViewModel::class.simpleName, e.toString())
                    TODO("Not yet implemented")
                }
            })
        }
    }

    suspend fun loadDiscount() {
        _isLoadingDiscount.value = true

        val discountsData = apolloClient.query(DiscountsQuery()).await()

        _isLoadingDiscount.value = false
        _discounts.value = discountsData.data?.discount
    }
}

class MainViewModelFactory(private val apolloClient: ApolloClient): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(apolloClient) as T
}