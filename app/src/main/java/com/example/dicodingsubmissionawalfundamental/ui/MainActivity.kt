package com.example.dicodingsubmissionawalfundamental.ui

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
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchBar.setText(USER_ID)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val searchViewText = searchView.text.toString()
                    if (searchViewText.isEmpty()) {
                        searchBar.setText(USER_ID)
                    } else {
                        searchBar.setText(searchView.text)
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

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.listUser.observe(this) { userData ->
            setUserData(userData)
        }
    }

    private fun setUserData(userData: List<ItemsItem?>?) {
        val adapter = UserAdapter()
        adapter.submitList(userData)
        binding.rvUser.adapter = adapter
    }

    companion object {
        var USER_ID = "charlesbin"
    }
}