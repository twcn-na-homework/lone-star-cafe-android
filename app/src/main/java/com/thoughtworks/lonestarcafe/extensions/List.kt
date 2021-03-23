package com.thoughtworks.lonestarcafe.extensions

import com.thoughtworks.lonestarcafe.type.ItemType

fun List<ItemType>.toDisplayString(): String {
    return this.joinToString { it.rawValue.toLowerCase().capitalize() }
}