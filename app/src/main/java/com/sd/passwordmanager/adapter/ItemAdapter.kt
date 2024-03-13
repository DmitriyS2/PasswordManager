package com.sd.passwordmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sd.passwordmanager.R
import com.sd.passwordmanager.databinding.ItemForRwBinding
import com.sd.passwordmanager.dto.ItemPassword

interface Listener {
    fun removeItem(item: ItemPassword)
    fun editItem(item: ItemPassword)
    fun showPassword(item: ItemPassword)
    fun hidePassword(item: ItemPassword)
}

class ItemAdapter(private val listener: Listener) :
    ListAdapter<ItemPassword, ItemAdapter.ItemHolder>(DiffCallback()) {

    class ItemHolder(item: View, private val listener: Listener) :
        RecyclerView.ViewHolder(item) {

        private val binding = ItemForRwBinding.bind(item)

        fun bind(item: ItemPassword) = with(binding) {

            title.text = item.title
            site.text = item.url
            password.isVisible = item.isOpenPassword

            Glide.with(avatar)
                .load(item.url + "/favicon.ico")
                .placeholder(R.drawable.download_64)
                .error(R.drawable.error_64)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .timeout(10_000)
                .into(avatar)

            buttonMenu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_item)
                    menu.setGroupVisible(R.id.hidden_password, item.isOpenPassword)
                    menu.setGroupVisible(R.id.open_password, !item.isOpenPassword)
                    setOnMenuItemClickListener { menu_item ->
                        when (menu_item.itemId) {
                            R.id.remove -> {
                                listener.removeItem(item)
                                true
                            }

                            R.id.edit -> {
                                listener.editItem(item)
                                true
                            }

                            R.id.show_password -> {
                                listener.showPassword(item)
                                true
                            }

                            R.id.hide_password -> {
                                listener.hidePassword(item)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_for_rw, parent, false)
        return ItemHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class DiffCallback : DiffUtil.ItemCallback<ItemPassword>() {
    override fun areItemsTheSame(oldItem: ItemPassword, newItem: ItemPassword): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemPassword, newItem: ItemPassword): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: ItemPassword, newItem: ItemPassword): Any =
        Payload()
}

data class Payload(
    val id: Int? = null
)