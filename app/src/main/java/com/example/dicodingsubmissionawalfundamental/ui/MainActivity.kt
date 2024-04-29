package com.example.dicodingsubmissionawalfundamental.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingsubmissionawalfundamental.R
import com.example.dicodingsubmissionawalfundamental.data.Result
import com.example.dicodingsubmissionawalfundamental.data.remote.response.ItemsItem
import com.example.dicodingsubmissionawalfundamental.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModelFactory.getInstance(application)
    }
    private var once: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)

        fun setUserData(userData: List<ItemsItem?>?) {
            val adapter = UserAdapter()
            adapter.submitList(userData)
            binding.rvUser.adapter = adapter
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            if (searchBar.text.isEmpty()) {
                tvNoData.visibility = View.VISIBLE
            }

            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val searchViewText = searchView.text.toString()
                    if (searchViewText.isEmpty()) {
                        searchBar.setText(USER_ID)
                    } else {
                        searchBar.setText(searchView.text)
                        tvNoData.visibility = View.GONE
                    }
                    searchView.hide()
                    USER_ID = searchBar.text.toString()
                    mainViewModel.findUser(USER_ID).observe(this@MainActivity) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    binding.apply {
                                        progressBar.visibility = View.VISIBLE
                                        rvUser.visibility = View.GONE
                                    }
                                }

                                is Result.Success -> {
                                    binding.apply {
                                        progressBar.visibility = View.GONE
                                        rvUser.visibility = View.VISIBLE
                                    }
                                    setUserData(result.data)
                                }

                                is Result.Error -> {
                                    binding.apply {
                                        progressBar.visibility = View.GONE
                                        rvUser.visibility = View.VISIBLE
                                    }
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Terjadi kesalahan " + result.error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                    false
                }

            rvUser.layoutManager = layoutManager
            rvUser.addItemDecoration(itemDecoration)

            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.switch_mode -> {
                        mainViewModel.getThemeSettings()
                            .observe(this@MainActivity) { isDarkModeActive ->
                                once = !isDarkModeActive
                            }
                        mainViewModel.saveThemeSetting(once)
                        true
                    }

                    R.id.favorite_menu -> {
                        val moveToFavorite = Intent(this@MainActivity, FavoriteActivity::class.java)
                        this@MainActivity.startActivity(moveToFavorite)
                        true
                    }

                    else -> false
                }
            }
        }

        mainViewModel.listUser.observe(this) { userData ->
            setUserData(userData)
        }

        mainViewModel.getThemeSettings()
            .observe(this@MainActivity) { isDarkModeActive ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.searchBar.menu.getItem(0).setIcon(R.drawable.baseline_favorite_24_white)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }


    }

    companion object {
        var USER_ID = "charlesbin"
    }
}