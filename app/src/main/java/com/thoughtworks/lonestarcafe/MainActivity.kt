package com.thoughtworks.lonestarcafe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.lonestarcafe.theme.LoneStarCafeTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appCompositionRoot = (application as LoneStarCafeApplication).appCompositionRoot

        setContent {
            LoneStarCafeTheme {
                LoneStarCafeApp(appCompositionRoot)
            }
        }
    }
}