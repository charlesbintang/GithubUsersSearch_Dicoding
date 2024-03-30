package com.example.dicodingsubmissionawalfundamental.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingsubmissionawalfundamental.R
import com.example.dicodingsubmissionawalfundamental.data.remote.response.ItemsItem
import com.example.dicodingsubmissionawalfundamental.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

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
                    mainViewModel.findUser(USER_ID)
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

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }


    private fun setUserData(userData: List<ItemsItem?>?) {
        val adapter = UserAdapter()
        adapter.submitList(userData)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            progressBar.visibility = if (state) View.VISIBLE else View.GONE
            rvUser.visibility = if (state) View.GONE else View.VISIBLE
        }
    }

    companion object {
        var USER_ID = "charlesbin"
    }
}