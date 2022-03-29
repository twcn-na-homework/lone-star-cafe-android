package com.thoughtworks.lonestarcafe.viewmodels

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.thoughtworks.lonestarcafe.MenuListQuery
import com.thoughtworks.lonestarcafe.type.ItemType
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ReceiptViewModelTest {
    private val receiptViewModel = ReceiptViewModel()

    @Test
    fun `should concat the description and price from the menu items`() {
        val menuList = listOf(MenuListQuery.Menu(
            id = "1001",
            description = "Coca-Cola",
            price = 499,
            type = ItemType.BEVERAGES,
            discountable = false,
            taxable = true
        ), MenuListQuery.Menu(
            id = "1002",
            description = "Pepsi Soda",
            price = 349,
            type = ItemType.BEVERAGES,
            discountable = false,
            taxable = true
        ))

        val itemDetailStr = receiptViewModel.getMenuItemDetailString(menuList)("1001")

        assertThat(itemDetailStr).isEqualTo("Coca-Cola -------- $4.99")
    }

    @Test
    fun `should return empty string when item id not match`() {
        val menuList = listOf(MenuListQuery.Menu(
            id = "1001",
            description = "Coca-Cola",
            price = 499,
            type = ItemType.BEVERAGES,
            discountable = false,
            taxable = true
        ), MenuListQuery.Menu(
            id = "1002",
            description = "Pepsi Soda",
            price = 349,
            type = ItemType.BEVERAGES,
            discountable = false,
            taxable = true
        ))

        val itemDetailStr = receiptViewModel.getMenuItemDetailString(menuList)("1005")

        assertThat(itemDetailStr).isEqualTo("")
    }
}