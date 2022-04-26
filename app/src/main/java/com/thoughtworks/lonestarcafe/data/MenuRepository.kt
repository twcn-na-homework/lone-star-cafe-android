package com.thoughtworks.lonestarcafe.data

import com.thoughtworks.lonestarcafe.MenuListQuery

interface MenuRepository {
    suspend fun getMenuList(): List<MenuListQuery.Menu>
}