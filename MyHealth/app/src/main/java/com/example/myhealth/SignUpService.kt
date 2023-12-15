package com.example.myhealth

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SignUpService {
    @FormUrlEncoded
    @POST("/api/users/join")
    fun requestRegister(
        @Field("userName") userName:String,
        @Field("password") password:String,
        @Field("nickName") nickName:String
    ) : Call<ResponseData>
}