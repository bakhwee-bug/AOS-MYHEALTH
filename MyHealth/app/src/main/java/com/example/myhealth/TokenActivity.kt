package com.example.myhealth

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.Call
import retrofit2.http.Body

//토큰
interface TokenActivity {
    @GET("/users/info")
    fun requestUser(
        @Header("Authorization") accessToken: String?
    ): Call<User>

    /*인바디 정보 조회*/
    @GET("/api/users/inbody")
    fun requestInBody(
        @Header("Authorization") accessToken: String?
    ): Call<ResponseInbody>


    /*일일 운동 조회*/
    @GET("/api/exercise/daily")
    fun requestExercise(
        @Header("Authorization") accessToken: String?,
        @Body jsonParams : String?
    ): Call<Exercise>

    /*일일 섭취 조회*/
    @GET("/api/intake/daily")
    fun requestIntake(
        @Header("Authorization") accessToken: String?,
        @Body jsonParams : String?
    ): Call<ResponseIntake>

    /*프로필조회*/
    @GET("/api/users/profile")
    fun requestProfile(
        @Header("Authorization") accessToken: String?
    ): Call<Profile>

}