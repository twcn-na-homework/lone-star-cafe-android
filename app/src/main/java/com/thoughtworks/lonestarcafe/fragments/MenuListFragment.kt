package com.thoughtworks.lonestarcafe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.thoughtworks.lonestarcafe.R
import com.thoughtworks.lonestarcafe.adapters.MenuAdapter
import com.thoughtworks.lonestarcafe.databinding.FragmentMenuListBinding
import com.thoughtworks.lonestarcafe.network.apollo.CustomizedApolloClient
import com.thoughtworks.lonestarcafe.viewmodels.MainViewModel
import com.thoughtworks.lonestarcafe.viewmodels.MainViewModelFactory
import com.thoughtworks.lonestarcafe.viewmodels.MenuListViewModel

class MenuListFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(CustomizedApolloClient.client)
    }

    private val menuListViewModel: MenuListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (mainViewModel.menuList.value == null) {
            lifecycleScope.launchWhenCreated {
                menuListViewModel.isLoadingMenuItems.value = true
                mainViewModel.loadMenuList()
                menuListViewModel.isLoadingMenuItems.value = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuAdapter = MenuAdapter(mainViewModel)
        val binding = DataBindingUtil.inflate<FragmentMenuListBinding>(inflater, R.layout.fragment_menu_list, container, false)

        return binding.apply {
            adapter = menuAdapter
            subscribeUi(menuAdapter)

            mainVm = mainViewModel
            menuListVm = menuListViewModel
            lifecycleOwner = viewLifecycleOwner
            submitButton.setOnClickListener {
                val action =
                    MenuListFragmentDirections.actionMenuListFragmentToReceiptFragment()
                view?.findNavController()?.navigate(action)
            }
        }.root
    }

    private fun subscribeUi(adapter: MenuAdapter) {
        mainViewModel.menuList.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}