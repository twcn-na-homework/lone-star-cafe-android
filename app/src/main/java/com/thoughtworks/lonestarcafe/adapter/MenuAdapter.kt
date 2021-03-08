package com.thoughtworks.lonestarcafe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.lonestarcafe.MenuListQuery
import com.thoughtworks.lonestarcafe.databinding.ListItemMenuBinding

class MenuAdapter: ListAdapter<MenuListQuery.Menu, MenuAdapter.MenuViewHolder>(MenuDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            ListItemMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = getItem(position)
        holder.bind(menuItem)
    }

    class MenuViewHolder(private val binding: ListItemMenuBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuListQuery.Menu) {
            binding.apply {
                menuItem = item
                executePendingBindings()
            }
        }
    }
}

private class MenuDiffCallback: DiffUtil.ItemCallback<MenuListQuery.Menu>() {
    override fun areItemsTheSame(
        oldItem: MenuListQuery.Menu,
        newItem: MenuListQuery.Menu
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MenuListQuery.Menu,
        newItem: MenuListQuery.Menu
    ): Boolean {
        return oldItem == newItem
    }

}