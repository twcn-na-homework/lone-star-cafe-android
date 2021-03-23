package com.thoughtworks.lonestarcafe.viewmodels

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
import com.apollographql.apollo.coroutines.await
import com.thoughtworks.lonestarcafe.DiscountsQuery
import com.thoughtworks.lonestarcafe.MenuListQuery
import com.thoughtworks.lonestarcafe.type.ItemType
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: MainViewModel

    @MockK
    private lateinit var mockApolloClient: ApolloClient

    @MockK
    private lateinit var menuListCall: ApolloQueryCall<MenuListQuery.Data>

    @MockK
    private lateinit var discountsCall: ApolloQueryCall<DiscountsQuery.Data>

    @MockK
    private lateinit var mockMenuListResponse: Response<MenuListQuery.Data>

    @MockK
    private lateinit var mockDiscountsResponse: Response<DiscountsQuery.Data>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        Dispatchers.setMain(testCoroutineDispatcher)

        every { mockApolloClient.query(any<MenuListQuery>()) } returns menuListCall
        every { mockApolloClient.query(any<DiscountsQuery>()) } returns discountsCall

        every { mockMenuListResponse.data?.menu } returns listOf(menu)
        every { mockDiscountsResponse.data?.discount } returns listOf(discount)

        every { menuListCall.enqueue(any<Callback<MenuListQuery.Data>>()) } answers {
            val callback: Callback<MenuListQuery.Data> = firstArg()
            callback.onResponse(mockMenuListResponse)
        }

        mockkStatic("com.apollographql.apollo.coroutines.CoroutinesExtensionsKt")

        coEvery { discountsCall.await() } coAnswers {
            delay(1)
            mockDiscountsResponse
        }

        viewModel = MainViewModel(mockApolloClient)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
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

    @Test
    fun shouldLoadDiscountsAndFillIntoTheLiveData() = runBlockingTest {
        val deferred = async {
            viewModel.loadDiscount()
        }

        assertThat(viewModel.isLoadingDiscount.value).isEqualTo(true)

        deferred.await()

        assertThat(viewModel.isLoadingDiscount.value).isEqualTo(false)
        assertThat(viewModel.discounts.value?.get(0)).isEqualTo(discount)
    }


    private val menu: MenuListQuery.Menu = MenuListQuery.Menu(
        id = "1",
        description = "test",
        type = ItemType.BEVERAGES,
        discountable = true,
        taxable = true,
        price = 499
    )

    private val discount: DiscountsQuery.Discount = DiscountsQuery.Discount(
        code = "FAKE_DISC",
        description = "This is a fake discount",
        discountPct = null,
        discountAmount = 500,
        applyOn = listOf(ItemType.BEVERAGES, ItemType.DISHES)
    )
}