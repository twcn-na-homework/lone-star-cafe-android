package com.thoughtworks.lonestarcafe.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObservers() {
    this.value = this.value
}