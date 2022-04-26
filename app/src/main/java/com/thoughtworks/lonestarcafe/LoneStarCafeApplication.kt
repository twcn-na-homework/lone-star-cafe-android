package com.thoughtworks.lonestarcafe

import android.app.Application
import com.thoughtworks.lonestarcafe.data.AppCompositionRoot
import com.thoughtworks.lonestarcafe.data.impl.AppCompositionRootImpl
import com.thoughtworks.lonestarcafe.network.apollo.CustomizedApolloClient

class LoneStarCafeApplication: Application() {
    lateinit var appCompositionRoot: AppCompositionRoot

    override fun onCreate() {
        super.onCreate()
        appCompositionRoot = AppCompositionRootImpl(apolloClient = CustomizedApolloClient.client)
    }
}