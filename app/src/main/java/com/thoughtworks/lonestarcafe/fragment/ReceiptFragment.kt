package com.thoughtworks.lonestarcafe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.thoughtworks.lonestarcafe.R
import com.thoughtworks.lonestarcafe.databinding.FragmentReceiptBinding

class ReceiptFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentReceiptBinding>(inflater, R.layout.fragment_receipt, container, false)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }
}