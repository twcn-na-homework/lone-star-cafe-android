package com.thoughtworks.lonestarcafe.dao

import com.thoughtworks.lonestarcafe.MenuListQuery

object MenuDao {
    private val _menuList: MutableList<MenuListQuery.Menu> = mutableListOf()

    val menuList: List<MenuListQuery.Menu>
        get() = _menuList

    fun insertMenus(menus: List<MenuListQuery.Menu>) {
        _menuList.addAll(menus)
    }

    fun findMenuById(id: String): MenuListQuery.Menu? {
        return _menuList.find { it.id == id }
    }
}