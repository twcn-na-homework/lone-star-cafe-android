package com.thoughtworks.lonestarcafe.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.hasSize
import com.apollographql.apollo.ApolloCall.Callback
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Response
import com.thoughtworks.lonestarcafe.MenuListQuery
import com.thoughtworks.lonestarcafe.type.ItemType
import com.thoughtworks.lonestarcafe.viewmodel.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @MockK
    private lateinit var mockApolloClient: ApolloClient

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        val call = mockk<ApolloQueryCall<MenuListQuery.Data>>("mockApolloQueryCall")

        every { mockApolloClient.query(any<MenuListQuery>()) } returns call

        val mockResponse: Response<MenuListQuery.Data> = mockk("test")

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
            assertThat(it).hasSize(2)
            assertThat(it[0]).equals(menu)
        }
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