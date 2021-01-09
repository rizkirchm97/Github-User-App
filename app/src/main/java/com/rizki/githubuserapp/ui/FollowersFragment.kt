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
import com.rizki.githubuserapp.databinding.FragmentFollowersBinding
import com.rizki.githubuserapp.util.isGone
import com.rizki.githubuserapp.viewmodel.FollowViewModel

class FollowersFragment : Fragment() {
    companion object {
        private val ARG_USERNAME = "username"
        private val TAG = FollowersFragment::class.java.simpleName

        fun newInstance(username: String?): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var followersViewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
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
        followersViewModel.setFollowers(s)
    }

    private fun initViewModel() {
        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowViewModel::class.java)
        followersViewModel.getFollowersData().observe(viewLifecycleOwner, { itemData ->
            if (itemData != null) {
                Log.d(TAG, itemData.toString())
                followersAdapter.setData(itemData)
                binding.progressBar.isGone()
            }
        })
    }

    private fun initRv() {
        followersAdapter = FollowersAdapter()
        followersAdapter.notifyDataSetChanged()
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.adapter = followersAdapter
    }

}