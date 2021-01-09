package com.rizki.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizki.githubuserapp.databinding.UserItemBinding
import com.rizki.githubuserapp.model.FollowingItem

class FollowingAdapter: RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    private val mData = ArrayList<FollowingItem>()
    fun setData(item : ArrayList<FollowingItem>) {
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }
    class FollowingViewHolder(private val itemBinding: UserItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: FollowingItem){
            with(itemBinding) {
                tvUsername.text = item.username
                Glide.with(itemView.context)
                    .load(item.avatarUrl)
                    .apply(RequestOptions().override(75, 75))
                    .into(civImgProfile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) = holder.bind(mData[position])


    override fun getItemCount(): Int = mData.size
}