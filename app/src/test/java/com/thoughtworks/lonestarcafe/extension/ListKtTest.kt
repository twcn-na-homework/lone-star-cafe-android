package com.thoughtworks.lonestarcafe.extension

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.thoughtworks.lonestarcafe.type.ItemType
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ListKtTest {
    @Test
    fun `should convert list of item type enum value to display string`() {
        val types = listOf(ItemType.BEVERAGES, ItemType.DISHES)

        val result = types.toDisplayString()

        assertThat(result).isEqualTo("Beverages, Dishes")
    }
}