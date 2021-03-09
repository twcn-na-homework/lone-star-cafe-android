package com.thoughtworks.lonestarcafe.viewmodel

import android.util.Log
import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.thoughtworks.lonestarcafe.MenuListQuery
import com.thoughtworks.lonestarcafe.extension.notifyObservers

class MainViewModel(private val apolloClient: ApolloClient) : ViewModel() {
    val menuList: MutableLiveData<List<MenuListQuery.Menu>> by lazy {
        MutableLiveData()
    }
    val selectItems: MutableLiveData<MutableMap<String, MenuListQuery.Menu>> by lazy {
        MutableLiveData<MutableMap<String, MenuListQuery.Menu>>(HashMap())
    }

    val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener { view: CompoundButton, isChecked: Boolean ->
        val menuItem = view.tag as MenuListQuery.Menu
        selectItems.value?.size

        selectItems.value?.apply {
            if (isChecked) {
                put(menuItem.id, menuItem)
            } else {
                remove(menuItem.id)
            }
        }
        selectItems.notifyObservers()
    }

    init {
        loadMenuList()
    }

    private fun loadMenuList() {
        apolloClient.query(MenuListQuery()).enqueue(object: ApolloCall.Callback<MenuListQuery.Data>() {
            override fun onResponse(response: Response<MenuListQuery.Data>) {
                menuList.postValue(response.data?.menu)
            }

            override fun onFailure(e: ApolloException) {
                Log.e(MainViewModel::class.simpleName, e.toString())
                TODO("Not yet implemented")
            }
        })
    }
}

class MainViewModelFactory(private val apolloClient: ApolloClient): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(apolloClient) as T
}