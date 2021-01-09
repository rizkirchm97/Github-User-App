package com.rizki.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizki.githubuserapp.databinding.UserItemBinding
import com.rizki.githubuserapp.model.UserItem

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val mData = ArrayList<UserItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(item: ArrayList<UserItem>) {
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }

    fun clearData() {
        mData.clear()
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val itemBinding: UserItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(user: UserItem) {
            with(itemBinding) {
                tvUsername.text = user.login
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions().override(75, 75))
                    .into(civImgProfile)

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItem)
    }

}