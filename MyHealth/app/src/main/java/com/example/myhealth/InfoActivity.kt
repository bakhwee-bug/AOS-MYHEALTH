package com.example.myhealth

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.Call


interface InfoActivity {
    @GET("/users/info")
    fun requestUser(
        @Header("access_token") accessToken: String?
    ): Call<User>
}