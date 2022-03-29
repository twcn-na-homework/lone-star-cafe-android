package com.thoughtworks.lonestarcafe.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.thoughtworks.lonestarcafe.DiscountsQuery
import com.thoughtworks.lonestarcafe.R
import com.thoughtworks.lonestarcafe.adapters.DiscountItemAdapter
import com.thoughtworks.lonestarcafe.databinding.FragmentReceiptBinding
import com.thoughtworks.lonestarcafe.network.apollo.CustomizedApolloClient
import com.thoughtworks.lonestarcafe.viewmodels.MainViewModel
import com.thoughtworks.lonestarcafe.viewmodels.MainViewModelFactory
import com.thoughtworks.lonestarcafe.viewmodels.ReceiptViewModel

class ReceiptFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(CustomizedApolloClient.client)
    }

    private val receiptViewModel: ReceiptViewModel by viewModels()

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

        binding.receiptVm = receiptViewModel
        binding.lifecycleOwner = viewLifecycleOwner
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
                                showDiscountSelectDialog(context, discounts)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.selectedMenuItems.observe(viewLifecycleOwner) { selectedItems ->
            receiptViewModel.selectedItemsStr.value = selectedItems.map(
                receiptViewModel.getMenuItemDetailString(mainViewModel.menuList.value)
            ).filter { it.isNotEmpty() }.joinToString("\n")
        }
    }

    private fun showDiscountSelectDialog(
        context: Context,
        discounts: List<DiscountsQuery.Discount>
    ) {
        val adapter = DiscountItemAdapter(context, discounts)
        MaterialAlertDialogBuilder(context).apply {
            setTitle(R.string.discount_dialog_title)
            setSingleChoiceItems(adapter, -1) { view, id ->
                mainViewModel.discounts.value?.get(id).let {
                    receiptViewModel.selectedDiscount.value = it
                }
                view.dismiss()
            }
        }.create().show()
    }
}