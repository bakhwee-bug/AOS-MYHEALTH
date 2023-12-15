package com.example.myhealth

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST("api/users/login")
    fun login(
        @Field("userName") userName:String,
        @Field("password") password:String
    ): Call<ResponseData>
}