package com.rizki.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizki.githubuserapp.databinding.UserItemBinding
import com.rizki.githubuserapp.model.FollowersItem

class FollowersAdapter: RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    private val mData = ArrayList<FollowersItem>()
    fun setData(item: ArrayList<FollowersItem>) {
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }


    class FollowersViewHolder(private val itemBinding: UserItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
            fun bind(item: FollowersItem) {
                with(itemBinding){
                    Glide.with(itemView.context)
                        .load(item.avatar)
                        .apply(RequestOptions().override(75,75))
                        .into(civImgProfile)
                    tvUsername.text = item.username
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) = holder.bind(mData[position])


    override fun getItemCount(): Int = mData.size

}