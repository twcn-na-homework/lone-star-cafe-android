package com.thoughtworks.lonestarcafe.ui

data class MenuPageUiState(
    val menuItems: List<MenuItemUiState> = listOf()
)

data class MenuItemUiState(
    val id: String,
    val description: String,
    val price: Int,
    val isSelected: Boolean = false
)
