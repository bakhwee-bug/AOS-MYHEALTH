package com.example.myhealth

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//백엔드로 전송
data class LoginRequest(
    @SerializedName("userName") val userName: String,
    @SerializedName("password") val password: String
)


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
        val intakes: MutableList<Intake>
    ) {
        data class Intake(
            val id: Int,
            val content: String,
            val calorie: Double
        )
    }
}

data class ResponseExercise(
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
        val exercises: MutableList<Exercise>
    ) {
        data class Exercise(
            val id: Int,
            val content: String,
            val calorie: Double
        )
    }
}


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

data class DelFood(
    val code: Int,
    val httpStatus: String,
    val message: String,
    val data: Profile
): Serializable
data class Profile(
    val id: Int,
    val birth: String,
    val gender: String,
    val height: Double,
    val weight: Double,
    val user: User
)

//개별 섭취, 운동 등록할 때 Body값
data class DaConCal(
    val date: String,
    val content: String,
    val calorie: Double
)

//인바디업데이트할때
data class InbodyData(
    val bmi: Double,
    val skeletalMuscle: Double,
    val fatPer: Double
)