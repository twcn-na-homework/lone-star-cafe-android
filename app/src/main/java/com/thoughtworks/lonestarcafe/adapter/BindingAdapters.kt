package com.thoughtworks.lonestarcafe.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

@BindingAdapter("setAdapter")
fun setAdapter(view: RecyclerView, adapter: Adapter<RecyclerView.ViewHolder>) {
    view.adapter = adapter
}