package com.thoughtworks.lonestarcafe.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.thoughtworks.lonestarcafe.MenuListQuery

class MainViewModel(private val apolloClient: ApolloClient) : ViewModel() {
    val menuList: MutableLiveData<List<MenuListQuery.Menu>> by lazy {
        MutableLiveData()
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