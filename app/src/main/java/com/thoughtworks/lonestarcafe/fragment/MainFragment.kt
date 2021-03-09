package com.thoughtworks.lonestarcafe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.thoughtworks.lonestarcafe.R
import com.thoughtworks.lonestarcafe.adapter.MenuAdapter
import com.thoughtworks.lonestarcafe.databinding.MainFragmentBinding
import com.thoughtworks.lonestarcafe.network.apollo.CustomizedApolloClient
import com.thoughtworks.lonestarcafe.viewmodel.MainViewModel
import com.thoughtworks.lonestarcafe.viewmodel.MainViewModelFactory

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(CustomizedApolloClient.client)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<MainFragmentBinding>(inflater, R.layout.main_fragment, container, false)

        val adapter = MenuAdapter(viewModel.onCheckedChangeListener)
        binding.adapter = adapter
        subscribeUi(adapter)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun subscribeUi(adapter: MenuAdapter) {
        viewModel.menuList.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}