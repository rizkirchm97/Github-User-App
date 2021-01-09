package com.rizki.githubuserapp.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rizki.githubuserapp.R
import com.rizki.githubuserapp.model.FollowersItem
import com.rizki.githubuserapp.model.FollowingItem
import com.rizki.githubuserapp.ui.FollowersFragment
import com.rizki.githubuserapp.ui.FollowingFragment

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var username: String? = null

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
    )

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowersFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
        }

        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }
}