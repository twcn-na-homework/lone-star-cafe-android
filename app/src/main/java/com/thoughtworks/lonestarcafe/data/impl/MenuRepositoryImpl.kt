package com.thoughtworks.lonestarcafe.data.impl

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.thoughtworks.lonestarcafe.MenuListQuery
import com.thoughtworks.lonestarcafe.dao.MenuDao
import com.thoughtworks.lonestarcafe.data.MenuRepository

class MenuRepositoryImpl(private val apolloClient: ApolloClient, private val menuDao: MenuDao): MenuRepository {

    override suspend fun getMenuList(): List<MenuListQuery.Menu> {
        var menus = menuDao.menuList

        if (menus.isEmpty()) {
            val response = apolloClient.query(MenuListQuery()).await()
            menus = response.data?.menu ?: listOf()

            menuDao.insertMenus(menus = menus)
        }

        return menus
    }
}