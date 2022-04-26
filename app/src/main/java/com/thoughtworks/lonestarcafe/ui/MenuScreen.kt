package com.thoughtworks.lonestarcafe.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.thoughtworks.lonestarcafe.viewmodels.MenuScreenViewModel

@Composable
fun MenuScreen(
    menuScreenViewModel: MenuScreenViewModel
) {
    LazyColumn {
        items(menuScreenViewModel.uiState.menuItems) {
            Column {
                Text(text = it.description)
            }
        }
    }
}