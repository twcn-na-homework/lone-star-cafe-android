package com.thoughtworks.lonestarcafe.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.lonestarcafe.data.MenuRepository
import com.thoughtworks.lonestarcafe.ui.MenuItemUiState
import com.thoughtworks.lonestarcafe.ui.MenuPageUiState
import kotlinx.coroutines.launch

class MenuScreenViewModel(private val menuRepository: MenuRepository): ViewModel() {
    var uiState by mutableStateOf(MenuPageUiState())
        private set

    init {
        fetchMenuList()
    }

    fun fetchMenuList() {
        viewModelScope.launch {
            val menuList = menuRepository.getMenuList()
            uiState = uiState.copy(
                menuItems = menuList.map {
                    MenuItemUiState(
                        id = it.id,
                        description = it.description,
                        price = it.price
                    )
                }
            )
        }
    }
}