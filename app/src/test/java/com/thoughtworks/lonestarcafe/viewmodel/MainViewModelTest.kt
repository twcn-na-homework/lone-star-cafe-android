package com.thoughtworks.lonestarcafe.viewmodel

import android.widget.CheckBox
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.apollographql.apollo.ApolloCall.Callback
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Response
import com.thoughtworks.lonestarcafe.MenuListQuery
import com.thoughtworks.lonestarcafe.type.ItemType
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: MainViewModel

    @MockK
    private lateinit var mockApolloClient: ApolloClient

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        Dispatchers.setMain(mainThreadSurrogate)

        val call = mockk<ApolloQueryCall<MenuListQuery.Data>>()

        every { mockApolloClient.query(any<MenuListQuery>()) } returns call

        val mockResponse = mockk<Response<MenuListQuery.Data>>()

        every { mockResponse.data?.menu } returns listOf(menu)

        every { call.enqueue(any<Callback<MenuListQuery.Data>>()) } answers {
            val callback: Callback<MenuListQuery.Data> = firstArg()
            callback.onResponse(mockResponse)
        }
        viewModel = MainViewModel(mockApolloClient)
    }

    @Test
    fun shouldObserveTheLiveDataChangeWhenInitTheViewModel() {
        viewModel.menuList.observeForever {
            assertThat(it).hasSize(1)
            assertThat(it[0]).isEqualTo(menu)
        }
    }

    @Test
    fun shouldAddMenuItemIdToSelectedItemsWhenCheckBoxIsChecked() {
        val mockedCheckBox = mockk<CheckBox>()
        every { mockedCheckBox.tag } returns menu

        viewModel.onCheckedChangeListener.onCheckedChanged(mockedCheckBox, true)

        viewModel.selectItems.observeForever {
            assertThat(it["1"]).isEqualTo(menu)
        }
    }

    @Test
    fun shouldRemoveMenuItemFromSelectedItemsWhenCheckBoxUnchecked() {
        val mockedCheckBox = mockk<CheckBox>()
        every { mockedCheckBox.tag } returns menu
        viewModel.selectItems.value?.put("1", menu)
        viewModel.selectItems.value?.put("2", menu)

        viewModel.onCheckedChangeListener.onCheckedChanged(mockedCheckBox, false)

        assertThat(viewModel.selectItems.value?.get("1")).isNull()
        assertThat(viewModel.selectItems.value?.get("2")).isNotNull()
    }

    private val menu: MenuListQuery.Menu = MenuListQuery.Menu(
        id = "1",
        description = "test",
        type = ItemType.BEVERAGES,
        discountable = true,
        taxable = true,
        price = 499
    )
}