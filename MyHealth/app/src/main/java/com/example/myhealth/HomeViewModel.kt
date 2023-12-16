package com.example.myhealth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
/*

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel {
    val TAG = "HomeViewModel"
    private val foodService = FoodClient.getRetrofitService()
    var job : Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }
    var data = MutableLiveData<List<FoodData>>()
    val foodLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        getFoodData()
    }

    private fun getFoodData(){
        loading.value=true

        job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = foodService.searchByProductName(getdate())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    data.postValue(response.body()?.boxOfficeResult?.dailyBoxOfficeList)
                    //Log.d(TAG, response.body()?.boxOfficeResult?.dailyBoxOfficeList.toString())
                    movieLoadError.postValue(null)
                    loading.postValue(false)

                } else {
                    onError("Error : ${response.message()}")

                }
            }
        }


    }
    출처: https://yeons4every.tistory.com/112 [나누는 개발 공부:티스토리]

    private fun onError(message: String) {
        foodLoadError.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared(){
        super.onCleared()
        job?.cancel()
    }

}*/
