package com.example.myhealth

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface LoginService {
    @POST("api/users/login")
    fun login(@Body request: LoginRequest): ResponseData
}