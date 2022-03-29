package com.thoughtworks.lonestarcafe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thoughtworks.lonestarcafe.DiscountsQuery
import com.thoughtworks.lonestarcafe.MenuListQuery

class ReceiptViewModel : ViewModel() {
    val selectedDiscount: MutableLiveData<DiscountsQuery.Discount> by lazy {
        MutableLiveData()
    }

    val selectedItemsStr: MutableLiveData<String> by lazy { MutableLiveData() }

    fun getMenuItemDetailString(menuList: List<MenuListQuery.Menu>?): (String) -> String {
        return { id: String ->
            val menu = menuList?.find { it.id == id }
            if (menu == null) "" else "${menu.description} -------- $${
                String.format(
                    "%.2f",
                    menu.price / 100.0
                )
            }"
        }
    }
}