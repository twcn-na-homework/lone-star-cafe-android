package com.thoughtworks.lonestarcafe

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) }
                )
            },
        ) {

            MenuScreen(viewModel)
        }
    }
}