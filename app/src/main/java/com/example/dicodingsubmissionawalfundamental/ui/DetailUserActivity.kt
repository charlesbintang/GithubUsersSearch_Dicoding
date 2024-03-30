package com.example.dicodingsubmissionawalfundamental.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dicodingsubmissionawalfundamental.R
import com.example.dicodingsubmissionawalfundamental.data.Result
import com.example.dicodingsubmissionawalfundamental.data.remote.response.DetailUserResponse
import com.example.dicodingsubmissionawalfundamental.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel> {
        DetailUserViewModelFactory.getInstance(application)
    }
    private var once = true

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

        val detailUsername = intent.getStringExtra(EXTRA_USERNAME)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = detailUsername.toString()
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        if (detailUsername != null) {
            detailUserViewModel.findDetailUser(detailUsername).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            setDetailUserData(result.data)
                        }

                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this, "Terjadi kesalahan " + result.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        detailUserViewModel.detailUserResponse.observe(this) { detailUser ->
            setDetailUserData(detailUser)
        }

        detailUserViewModel.getFavoriteUserByUsername(detailUsername.toString())
            .observe(this) { favoriteUser ->
                once = if (favoriteUser != null) {
                    binding.fabFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.fabFavorite.context, R.drawable.baseline_favorite_24
                        )
                    )
                    false
                } else {
                    binding.fabFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.fabFavorite.context, R.drawable.baseline_favorite_border_24
                        )
                    )
                    true
                }
            }

        binding.fabFavorite.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_favorite -> {
                val detailUsername = intent.getStringExtra(EXTRA_USERNAME)
                val detailAvatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)
                if (once) {
                    detailUserViewModel.setFavoriteUser(
                        detailUsername.toString(), detailAvatarUrl.toString()
                    )
                } else {
                    detailUserViewModel.deleteFavoriteUser(
                        detailUsername.toString(), detailAvatarUrl.toString()
                    )
                }


//                detailUserViewModel.getFavoriteUserByUsername(detailUsername.toString())
//                    .observe(this) {
//                        if (it != null && !once) {
//                            detailUserViewModel.deleteFavoriteUser(
//                                detailUsername.toString(),
//                                detailAvatarUrl.toString()
//                            )
//                            once = true
//                        } else {
//                            detailUserViewModel.setFavoriteUser(
//                                detailUsername.toString(),
//                                detailAvatarUrl.toString()
//                            )
//                        }
//                    }
            }
        }
    }

    companion object {
        const val EXTRA_USERNAME = "charlesbintang"
        const val EXTRA_AVATAR_URL = "https://avatars.githubusercontent.com/u/24184036?v=4"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1, R.string.tab_text_2
        )
    }
}