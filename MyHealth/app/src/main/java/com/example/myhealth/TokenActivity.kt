package com.example.myhealth

import retrofit2.Call
import retrofit2.http.*

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
        @Query("date") date: String?,
    ): Call<ResponseExercise>

    /*일일 섭취 조회*/
    @GET("/api/intake/daily")
    fun requestIntake(
        @Header("Authorization") accessToken: String?,
        @Query("date") date: String?,
    ): Call<ResponseIntake>

    /*프로필조회*/
    @GET("/api/users/profile")
    fun requestProfile(
        @Header("Authorization") accessToken: String?
    ): Call<Profile>

    /*섭취 등록*/
    @POST("/api/intake/task")
    fun eatFood(
        @Header("Authorization") accessToken: String?,
        @Body DaConCal: DaConCal
        ):Call<ResponseData>

    /*운동 등록*/
    @POST("/api/exercise/task")
    fun doExercise(
        @Header("Authorization") accessToken: String?,
        @Body DaConCal: DaConCal
    ):Call<ResponseData>

    /*인바디 업데이트*/
    @PUT("/api/users/updateInbody")
    fun updateInbody(
        @Header("Authorization") accessToken: String,
        @Body inbodyData: InbodyData
    ): Call<ResponseInbody>

    /*섭취 삭제*/
    @DELETE("/api/intake/task")
    fun deleteIntakeTask(
        @Header("Authorization") accessToken: String
    ): Call<ResponseInbody>

    /*운동 삭제*/
    @DELETE("/api/exercise/task")
    fun deleteExerciseTask(
        @Header("Authorization") accessToken: String
    ): Call<ResponseInbody>
}