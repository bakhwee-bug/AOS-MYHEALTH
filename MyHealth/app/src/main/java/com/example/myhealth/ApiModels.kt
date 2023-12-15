package com.example.myhealth

data class FoodListResponse(
    var total_count : Int,
    val result: List<FoodDetailResponse>
)

data class FoodDetailResponse(
    val DESC_KOR: String,
    val SERVING_SIZE: Int,
    val SERVING_UNIT: String,
    val NUTR_CONT1: Float
)