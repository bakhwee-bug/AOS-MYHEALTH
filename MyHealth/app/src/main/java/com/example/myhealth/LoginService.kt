package com.example.myhealth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/users/login")
    fun login(
        @Body jsonParams : LoginRequest,
    ): Call<ResponseData>
}