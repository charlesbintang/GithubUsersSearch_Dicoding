package com.example.dicodingsubmissionawalfundamental.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingsubmissionawalfundamental.data.remote.response.ItemsItem
import com.example.dicodingsubmissionawalfundamental.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        val adapter = UserAdapter()
        with(binding) {
            rvFavoriteUsers.layoutManager = layoutManager
            rvFavoriteUsers.addItemDecoration(itemDecoration)
        }
        favoriteViewModel.getAllFavoriteUser().observe(this) { userData ->
            val items = arrayListOf<ItemsItem>()
            userData.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            adapter.submitList(items)
            binding.rvFavoriteUsers.adapter = adapter
        }
    }
}