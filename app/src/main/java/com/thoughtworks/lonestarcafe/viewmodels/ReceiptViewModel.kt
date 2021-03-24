package com.thoughtworks.lonestarcafe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thoughtworks.lonestarcafe.DiscountsQuery

class ReceiptViewModel : ViewModel() {
    val selectedDiscount: MutableLiveData<DiscountsQuery.Discount> by lazy {
        MutableLiveData()
    }
}