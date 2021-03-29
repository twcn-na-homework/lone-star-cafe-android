package com.thoughtworks.lonestarcafe.viewmodels

import android.widget.CheckBox
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MenuListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: MenuListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        Dispatchers.setMain(testCoroutineDispatcher)

        viewModel = MenuListViewModel()
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
}