package com.example.myhealth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/api/users/join")
    fun requestRegister(
        @Body jsonParams : User,
        ) : Call<ResponseData>
}