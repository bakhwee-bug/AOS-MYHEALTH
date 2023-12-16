package com.example.myhealth

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.Call

//토큰
interface InfoActivity {
    @GET("/users/info")
    fun requestUser(
        @Header("Authorization") accessToken: String?
    ): Call<User>
}