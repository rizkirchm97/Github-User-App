package com.rizki.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizki.githubuserapp.adapter.FollowersAdapter
import com.rizki.githubuserapp.adapter.FollowingAdapter
import com.rizki.githubuserapp.databinding.FragmentFollowingBinding
import com.rizki.githubuserapp.util.isGone
import com.rizki.githubuserapp.viewmodel.FollowViewModel

class FollowingFragment : Fragment() {

    companion object {
        private val ARG_USERNAME = "username"
        private val TAG = FollowingFragment::class.java.simpleName

        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }
    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var followingViewModel: FollowViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var username = ""
        if (arguments != null) {
            username = arguments?.getString(ARG_USERNAME, "").toString()
        }

        initRv()
        initViewModel()
        setUsername(username)
    }

    private fun setUsername(s: String?) {
        followingViewModel.setFollowing(s)
    }

    private fun initViewModel() {
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowViewModel::class.java)
        followingViewModel.getFollowingData().observe(viewLifecycleOwner, { itemData ->
            if (itemData != null) {
                Log.d(TAG, itemData.toString())
                followingAdapter.setData(itemData)
                binding.progressBar.isGone()
            }
        })
    }

    private fun initRv() {
        followingAdapter = FollowingAdapter()
        followingAdapter.notifyDataSetChanged()
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter = followingAdapter
    }

}