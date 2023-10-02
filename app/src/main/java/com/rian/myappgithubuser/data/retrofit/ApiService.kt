package com.rian.myappgithubuser.data.retrofit

import com.rian.myappgithubuser.data.response.DetailUserResponse
import com.rian.myappgithubuser.data.response.GithubResponse
import com.rian.myappgithubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
//    @Headers("Authorization: token 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")
    @Headers("Authorization: token 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")

    fun getUsers(
        @Query("q") username: String
    ) : Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>


}

