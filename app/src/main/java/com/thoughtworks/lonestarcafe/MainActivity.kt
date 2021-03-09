package com.thoughtworks.lonestarcafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thoughtworks.lonestarcafe.fragment.MenuListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}