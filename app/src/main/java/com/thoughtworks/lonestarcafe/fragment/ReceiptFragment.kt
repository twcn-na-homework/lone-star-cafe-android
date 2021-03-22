package com.thoughtworks.lonestarcafe.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.thoughtworks.lonestarcafe.R
import com.thoughtworks.lonestarcafe.databinding.FragmentReceiptBinding
import com.thoughtworks.lonestarcafe.network.apollo.CustomizedApolloClient
import com.thoughtworks.lonestarcafe.viewmodel.MainViewModel
import com.thoughtworks.lonestarcafe.viewmodel.MainViewModelFactory

class ReceiptFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(CustomizedApolloClient.client)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (mainViewModel.discounts.value == null) {
            lifecycleScope.launchWhenCreated {
                mainViewModel.loadDiscount()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentReceiptBinding>(inflater, R.layout.fragment_receipt, container, false)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        mainViewModel.discounts.observe(viewLifecycleOwner) { discounts ->
            if (!discounts.isNullOrEmpty()) {
                binding.toolbar.apply {
                    inflateMenu(R.menu.receipt_menu)
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.discount -> {
                                showDiscountSelectDialog(
                                    context,
                                    discounts.map { disc -> disc.description }.toTypedArray(),
                                    0
                                )
                                true
                            }
                            else -> false
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun showDiscountSelectDialog(
        context: Context,
        discounts: Array<String>,
        selectedDiscount: Int
    ) {
        MaterialAlertDialogBuilder(context).apply {
            setTitle(R.string.discount_dialog_title)
            setSingleChoiceItems(discounts, selectedDiscount) { view, id ->
                println(id)
                view.dismiss()
            }
        }.create().show()
    }
}