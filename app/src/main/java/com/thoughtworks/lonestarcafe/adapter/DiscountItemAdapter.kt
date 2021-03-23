package com.thoughtworks.lonestarcafe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.thoughtworks.lonestarcafe.DiscountsQuery
import com.thoughtworks.lonestarcafe.databinding.ListItemDiscountBinding

class DiscountItemAdapter(
    context: Context,
    private val discounts: List<DiscountsQuery.Discount>
) : ArrayAdapter<DiscountsQuery.Discount>(context, 0, discounts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ListItemDiscountBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.discount = discounts[position]
        return binding.root
    }
}