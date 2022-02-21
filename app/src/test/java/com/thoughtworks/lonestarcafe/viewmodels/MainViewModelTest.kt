package com.thoughtworks.lonestarcafe.viewmodels

import android.widget.CheckBox
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.thoughtworks.lonestarcafe.DiscountsQuery
import com.thoughtworks.lonestarcafe.MenuListQuery
import com.thoughtworks.lonestarcafe.type.ItemType
import io.mockk.*
import io.mockk.impl.annotations.MockK
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

        every { mockApolloClient.query(ofType(MenuListQuery::class)) } returns menuListCall
        every { mockApolloClient.query(ofType(DiscountsQuery::class)) } returns discountsCall

        every { mockMenuListResponse.data?.menu } returns listOf(menu)
        every { mockDiscountsResponse.data?.discount } returns listOf(discount)

        mockkStatic("com.apollographql.apollo.coroutines.CoroutinesExtensionsKt")

        coEvery { menuListCall.await() } coAnswers {
            delay(1)
            mockMenuListResponse
        }
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
    fun `should load menu items and fill into the LiveData`() = runBlockingTest {
        val deferred = async {
            viewModel.loadMenuList()
        }

        deferred.await()

        assertThat(viewModel.menuList.value?.get(0)).isEqualTo(menu)
    }

    @Test
    fun `should load discounts and fill into the LiveData`() = runBlockingTest {
        val deferred = async {
            viewModel.loadDiscount()
        }

        assertThat(viewModel.isLoadingDiscount.value).isEqualTo(true)

        deferred.await()

        assertThat(viewModel.isLoadingDiscount.value).isEqualTo(false)
        assertThat(viewModel.discounts.value?.get(0)).isEqualTo(discount)
    }

    @Test
    fun `should add menuItemId to selected items when CheckBox is checked`() {
        val mockedCheckBox = mockk<CheckBox>()
        every { mockedCheckBox.tag } returns "1001"

        viewModel.onCheckedChangeListener.onCheckedChanged(mockedCheckBox, true)

        viewModel.selectedMenuItems.observeForever {
            assertThat(it[0]).isEqualTo("1001")
        }
    }

    @Test
    fun `should remove MenuItem from selectedItems when CheckBox unchecked`() {
        val mockedCheckBox = mockk<CheckBox>()
        every { mockedCheckBox.tag } returns "1001"
        viewModel.selectedMenuItems.value?.add("1001")
        viewModel.selectedMenuItems.value?.add("1002")

        viewModel.onCheckedChangeListener.onCheckedChanged(mockedCheckBox, false)

        assertThat(viewModel.selectedMenuItems.value?.size).isEqualTo(1)
        assertThat(viewModel.selectedMenuItems.value?.get(0)).isEqualTo("1002")
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