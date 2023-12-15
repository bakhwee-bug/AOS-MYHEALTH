package com.example.myhealth

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//백엔드로 전송
data class LoginRequest(
    @SerializedName("userName") val userName: String,
    @SerializedName("password") val password: String
)

data class ResponseData(
    val code: Int,
    val httpStatus: String,
    val message: String,
    val data: Data
): Serializable

data class Data(
    @SerializedName("token") val token: String
)

data class User(
    val userName: String,
    val password: String,
    val nickName: String
): Serializable

