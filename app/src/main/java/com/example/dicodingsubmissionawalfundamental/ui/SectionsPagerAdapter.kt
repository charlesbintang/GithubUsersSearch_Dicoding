package com.example.dicodingsubmissionawalfundamental.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = DetailFollowersFollowingUserFragment()
        fragment.arguments = Bundle().apply {
            putInt(DetailFollowersFollowingUserFragment.ARG_POSITION, position + 1)
            putString(DetailFollowersFollowingUserFragment.ARG_USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}