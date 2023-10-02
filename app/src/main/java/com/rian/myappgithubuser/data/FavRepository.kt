package com.rian.myappgithubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.rian.myappgithubuser.data.database.FavoriteDao
import com.rian.myappgithubuser.data.database.FavoriteRoomDatabase
import com.rian.myappgithubuser.data.database.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/* Kelas ini berfungsi sebagai penghubung antara
ViewModel dengan database atau resource data. */

class FavRepository (application: Application) {
    private val mFavoriteDao: FavoriteDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavoriteDao.getFav()

    fun insert(favorite: FavoriteUser) {
        executorService.execute { mFavoriteDao.insertFav(favorite) }
    }

    fun delete(favorite: FavoriteUser) {
        executorService.execute { mFavoriteDao.deleteFav(favorite) }
    }

}