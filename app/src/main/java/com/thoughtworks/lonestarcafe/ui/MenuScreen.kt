package com.thoughtworks.lonestarcafe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.thoughtworks.lonestarcafe.R
import com.thoughtworks.lonestarcafe.viewmodels.MenuScreenViewModel
import java.util.*
import kotlin.concurrent.schedule

@Composable
fun MenuScreen(
    menuScreenViewModel: MenuScreenViewModel
) {
    val selectedItemCountState = remember { mutableStateOf(0) }
    Timer("Timer", true).schedule(5000) {
        selectedItemCountState.value = 3
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        bottomBar = {
            BottomOrderSummaryBar(selectedItemCountState.value)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.list_item_margin)),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.list_item_margin))
        ) {
            items(menuScreenViewModel.uiState.menuItems) {
                MenuItem(menuItemUiState = it)
            }
        }
    }
}