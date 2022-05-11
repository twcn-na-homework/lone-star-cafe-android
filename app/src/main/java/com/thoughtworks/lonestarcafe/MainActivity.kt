package com.thoughtworks.lonestarcafe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.thoughtworks.lonestarcafe.theme.LoneStarCafeTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appCompositionRoot = (application as LoneStarCafeApplication).appCompositionRoot

        setContent {
            CompositionLocalProvider(LocalContext provides this) {
                LoneStarCafeTheme {
                    LoneStarCafeApp(appCompositionRoot)
                }
            }
        }
    }
}