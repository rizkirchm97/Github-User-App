package com.rizki.githubuserapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizki.consumerapp.databinding.UserItemBinding
import com.rizki.githubuserapp.model.UserItem
import com.rizki.githubuserapp.util.CustomOnItemClickListener

class FavAdapter(private val activity: Activity) : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {
    var listFavUser = ArrayList<UserItem>()
        set(listFavUser) {
            if (listFavUser.size > 0) {
                this.listFavUser.clear()
            }
            this.listFavUser.addAll(listFavUser)
            notifyDataSetChanged()
        }

    fun addItem(userItem: UserItem) {
        this.listFavUser.add(userItem)
        notifyItemInserted(this.listFavUser.size - 1)
    }

    fun removeItem(position: Int) {
        this.listFavUser.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavUser.size)
    }

    inner class FavViewHolder(private val itemBinding: UserItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

            fun bind(userItem: UserItem) {
                with(itemBinding) {
                    Glide.with(itemView.context)
                        .load(userItem.avatarUrl)
                        .apply(RequestOptions().override(75, 75))
                        .into(civImgProfile)
                    tvUsername.text = userItem.login
                    itemView.setOnClickListener(CustomOnItemClickListener(adapterPosition,
                    object : CustomOnItemClickListener.OnItemClickCallback {
                        override fun onItemClicked(view: View, position: Int) {
                        }
                    }))
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavAdapter.FavViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavAdapter.FavViewHolder, position: Int) {
        holder.bind(listFavUser[position])
    }

    override fun getItemCount(): Int = listFavUser.size
}