package com.example.myhealth

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//백엔드로 전송
data class LoginRequest(
    @SerializedName("userName") val userName: String,
    @SerializedName("password") val password: String
)
//인바디, 섭취 조회할 때
data class Daily(
    val data: String
): Serializable

//받는 data
data class ResponseData(
    val code: Int,
    val httpStatus: String,
    val message: String,
    val data: Data
): Serializable

data class ResponseInbody(
    val code: Int,
    val httpStatus: String,
    val message: String,
    val data: Inbody,
): Serializable

data class ResponseIntake(
    val code: Int,
    val httpStatus: String,
    val message: String,
    val data: Calorie
) {
    data class Calorie(
        val userName: String,
        val date: String,
        val targetCalorie: Double,
        val currentCalorie: Double,
        val intakes: List<Intake>
    ) {
        data class Intake(
            val id: Int,
            val content: String,
            val calorie: Double
        )
    }
}


data class ErrorResponse(
    val errorName: String,
    val errorCode: Int,
    val message: String
): Serializable


data class Data(
    @SerializedName("token") val token: String
)

data class User(
    val userName: String,
    val password: String,
    val nickName: String
): Serializable

data class Inbody(
    val id: Int,
    val bmi: Double?,
    val skeletalMuscle: Double?,
    val fatPer: Double?,
    val user: User
): Serializable

data class Exercise(
    val id: Int,
    val content: String,
    val calorie: Double
)


data class Profile(
    val id: Int,
    val birth: String,
    val gender: String,
    val height: Double,
    val weight: Double,
    val user: User
)

