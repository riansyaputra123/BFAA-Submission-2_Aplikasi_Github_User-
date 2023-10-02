package com.rian.myappgithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rian.myappgithubuser.R
import com.rian.myappgithubuser.adapter.FavoriteAdapter
import com.rian.myappgithubuser.data.database.FavoriteUser
import com.rian.myappgithubuser.databinding.ActivityFavoriteBinding
import com.rian.myappgithubuser.databinding.ActivityMainBinding
import com.rian.myappgithubuser.viewmodel.FavoriteViewModel
import com.rian.myappgithubuser.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {


    lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager


        viewModel = obtainViewModel(this@FavoriteActivity)

        viewModel.getAllFavorite().observe(this) {
            val adapter = FavoriteAdapter()
            adapter.submitList(it)
            binding.rvUser.adapter = adapter
        }

        binding.btnBack.setOnClickListener {
            finish()
        }


    }


    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}