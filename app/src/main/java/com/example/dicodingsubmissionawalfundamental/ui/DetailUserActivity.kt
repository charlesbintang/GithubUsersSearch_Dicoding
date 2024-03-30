package com.example.dicodingsubmissionawalfundamental.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dicodingsubmissionawalfundamental.R
import com.example.dicodingsubmissionawalfundamental.data.remote.response.DetailUserResponse
import com.example.dicodingsubmissionawalfundamental.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.switch_mode -> {
                    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    true
                }

                else -> false
            }
        }

        val detailUserData = intent.getStringExtra(EXTRA_USERNAME)

        val detailUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailUserViewModel::class.java]

        if (detailUserData != null) {
            detailUserViewModel.findDetailUser(detailUserData)
        }


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = detailUserData.toString()
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailUserViewModel.detailUserResponse.observe(this) { detailUser ->
            setDetailUserData(detailUser)
        }
    }

    private fun setDetailUserData(detailUserdata: DetailUserResponse) {
        binding.apply {
            Glide.with(this@DetailUserActivity).load(detailUserdata.avatarUrl).into(civUserProfile)
            tvUserName.text = detailUserdata.login
            tvUserFullName.text = detailUserdata.name
            tvUserFollowers.text = buildString {
                append(detailUserdata.followers.toString())
                append(" Followers")
            }
            tvUserFollowing.text = buildString {
                append(detailUserdata.following.toString())
                append(" Following")
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "charlesbintang"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}