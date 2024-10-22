package com.example.retrofitogreniyorum

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/users")
    fun getData() : Call<List<User>>
}