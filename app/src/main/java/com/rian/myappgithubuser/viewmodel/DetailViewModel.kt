package com.rian.myappgithubuser.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rian.myappgithubuser.data.FavRepository
import com.rian.myappgithubuser.data.database.FavoriteUser
import com.rian.myappgithubuser.data.response.DetailUserResponse
import com.rian.myappgithubuser.data.response.ItemsItem
import com.rian.myappgithubuser.data.retrofit.ApiConfig
import com.rian.myappgithubuser.ui.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application): ViewModel() {



    private val mFavoriteRepository: FavRepository = FavRepository(application)

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollower = MutableLiveData<List<ItemsItem>>()
    val userFollower: LiveData<List<ItemsItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val userFollowing: LiveData<List<ItemsItem>> = _listFollowing

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean> = _isFavorite

    var detailIsLoading = false
    var followerIsLoading = false
    var followingIsLoading = false


    fun getDetailUser(username: String) {
        if (!detailIsLoading) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getDetailUser(username)
            client.enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _detailUser.postValue(response.body())
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            detailIsLoading = true
        }
    }


    fun getFollower(username: String) {
        if (!followerIsLoading ) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getFollowers(username)
            client.enqueue(object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _listFollower.postValue(response.body())

                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            followerIsLoading = true
        }
    }

    fun getFollowing(username: String) {
        if (!followingIsLoading ) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getFollowing(username)
            client.enqueue(object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _listFollowing.postValue(response.body())
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            followingIsLoading = true
        }
    }

    fun getListFav(): LiveData<List<FavoriteUser>> = mFavoriteRepository.getAllFavorite()

    fun setIsFavorite(isFavorite: Boolean){
        _isFavorite.value = isFavorite
    }

    private fun addFavorite(fav_user: FavoriteUser){
        setIsFavorite(true)
        mFavoriteRepository.insert(fav_user)
    }

    private fun removeFavorite(fav_user: FavoriteUser){
        setIsFavorite(false)
        mFavoriteRepository.delete(fav_user)
    }

    fun updateFavUser(favUser: FavoriteUser, activity: DetailActivity){
        val fail_message =  "Remove User from Favorite"
        val succes_message =  "Add User to Favorite"
        if( isFavorite.value != true ){
            addFavorite(favUser)
            Toast.makeText(activity, succes_message, Toast.LENGTH_SHORT).show()
        }else{
            removeFavorite(favUser)
            Toast.makeText(activity, fail_message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}