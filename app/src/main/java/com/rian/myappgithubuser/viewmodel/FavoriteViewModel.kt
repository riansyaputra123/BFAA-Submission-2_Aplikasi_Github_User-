package com.rian.myappgithubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rian.myappgithubuser.data.FavRepository
import com.rian.myappgithubuser.data.database.FavoriteUser

class FavoriteViewModel (application: Application): ViewModel() {
    private val mFavoriteRepository: FavRepository = FavRepository(application)

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavoriteRepository.getAllFavorite()
}