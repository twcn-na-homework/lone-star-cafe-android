package com.thoughtworks.lonestarcafe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.lonestarcafe.R
import com.thoughtworks.lonestarcafe.viewmodels.MenuScreenViewModel

@Composable
fun MenuScreen(
    menuScreenViewModel: MenuScreenViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(menuScreenViewModel.uiState.menuItems) {
                MenuItem(menuItemUiState = it)
            }
        }
    }
}