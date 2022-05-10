package com.thoughtworks.lonestarcafe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thoughtworks.lonestarcafe.viewmodels.MenuScreenViewModel

@Composable
fun MenuScreen(
    menuScreenViewModel: MenuScreenViewModel
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 12.dp)
    ) {
        items(menuScreenViewModel.uiState.menuItems) {
            MenuItem(menuItemUiState = it)
        }
    }
}