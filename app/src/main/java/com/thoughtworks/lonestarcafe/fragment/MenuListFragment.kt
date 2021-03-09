package com.thoughtworks.lonestarcafe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.thoughtworks.lonestarcafe.R
import com.thoughtworks.lonestarcafe.adapter.MenuAdapter
import com.thoughtworks.lonestarcafe.databinding.FragmentMenuListBinding
import com.thoughtworks.lonestarcafe.network.apollo.CustomizedApolloClient
import com.thoughtworks.lonestarcafe.viewmodel.MainViewModel
import com.thoughtworks.lonestarcafe.viewmodel.MainViewModelFactory

class MenuListFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(CustomizedApolloClient.client)
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

            viewModel = mainViewModel
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