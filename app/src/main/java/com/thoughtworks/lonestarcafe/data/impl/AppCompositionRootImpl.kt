package com.thoughtworks.lonestarcafe.data.impl

import com.apollographql.apollo.ApolloClient
import com.thoughtworks.lonestarcafe.dao.MenuDao
import com.thoughtworks.lonestarcafe.data.AppCompositionRoot
import com.thoughtworks.lonestarcafe.data.MenuRepository

class AppCompositionRootImpl(private val apolloClient: ApolloClient): AppCompositionRoot {
    override val menuRepository: MenuRepository by lazy {
        MenuRepositoryImpl(apolloClient = apolloClient, menuDao = MenuDao)
    }
}