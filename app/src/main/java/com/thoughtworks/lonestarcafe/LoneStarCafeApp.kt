package com.thoughtworks.lonestarcafe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.lonestarcafe.data.AppCompositionRoot
import com.thoughtworks.lonestarcafe.theme.LoneStarCafeTheme
import com.thoughtworks.lonestarcafe.ui.MenuScreen
import com.thoughtworks.lonestarcafe.viewmodels.MenuScreenViewModel

@Composable
fun LoneStarCafeApp(compositionRoot: AppCompositionRoot) {
    LoneStarCafeTheme {
        val viewModel = remember {
            MenuScreenViewModel(compositionRoot.menuRepository)
        }
        MenuScreen(viewModel)
    }
}