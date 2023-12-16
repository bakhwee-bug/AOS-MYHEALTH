package com.example.myhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


var token : String? = ""

class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = intent.getStringExtra("token")
        var retrofit: Retrofit = RetrofitClient.getInstance()
        val userservice : TokenActivity = retrofit.create(TokenActivity::class.java)

        val BearerToken = "Bearer $token"
        val callInbody = userservice.requestInBody(BearerToken)
        val callIntake = userservice.requestIntake(BearerToken, "2023-12-25")
        val callExercise = userservice.requestExercise(BearerToken, "2023-12-25")
        //인바디 조회
        callInbody.enqueue(object : Callback<ResponseInbody>{
            override fun onResponse(call: Call<ResponseInbody>, response: Response<ResponseInbody>) {
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity_callInbody", apiResponse.message)
                            var inbody = apiResponse?.data
                            summary_bmi_num.text = inbody?.bmi.toString()
                            summary_skeletalMuscle_num.text = inbody?.bmi.toString()
                            summary_fatPer_num.text = inbody?.bmi.toString()
                            some_id.text = inbody?.user?.nickName.toString() + "님"
                        }
                    }


                }
            }

            override fun onFailure(call: Call<ResponseInbody>, t: Throwable) {
                Toast.makeText(this@MainActivity, "통신 실패", Toast.LENGTH_SHORT).show()
            }

        })
        //일일 섭취량 조회
        callIntake.enqueue(object : Callback<ResponseIntake>{
            override fun onResponse(call: Call<ResponseIntake>, response: Response<ResponseIntake>
            ) {
                Log.d("MainActivity_callIntake", "여기까지됨")
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity_callIntake", apiResponse.message)
                            var intake = apiResponse?.data
                            //오늘 섭취 칼로리
                            today_intake.text = intake?.currentCalorie.toString()
                            //목표 칼로리
                            target_intake.text = intake?.targetCalorie.toString()
                        }
                        400->{
                            Log.d("MainActivity_callIntake", apiResponse.message)
                        }
                    }


                }else{
                    //통신 실패
                    try {
                        val body = response.errorBody()!!.string()
                        Log.e("Login:User", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<ResponseIntake>, t: Throwable) {
                Log.d("MainActivity_callIntake", t.message.toString())
            }
        })
        //일일 운동 조회
        callExercise.enqueue(object : Callback<ResponseExercise>{
            override fun onResponse(call: Call<ResponseExercise>, response: Response<ResponseExercise>
            ) {
                Log.d("MainActivity_callExercise", "여기까지됨")
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity_callExercise", apiResponse.message)
                            var exercise = apiResponse?.data
                            //오늘 소모칼로리
                            today_exercise.text = exercise?.currentCalorie.toString()
                            //목표 칼로리
                            target_exercise.text = exercise?.targetCalorie.toString()
                        }
                        400->{
                            Log.d("MainActivity_callExercise", apiResponse.message)
                        }
                    }
            }else{
                    //통신 실패
                    try {
                        val body = response.errorBody()!!.string()
                        Log.e("Login:User", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            }

            override fun onFailure(call: Call<ResponseExercise>, t: Throwable) {
                Log.d("MainActivity_callExercise", t.message.toString())
            }
        })

        //식단기록 블록 누르면 Add 페이지로 이동
        btn_food.setOnClickListener{
            val intent = Intent(this@MainActivity, IntakeRecordActivity::class.java).apply {
                putExtra("BearerToken", BearerToken)
            }
            startActivity(intent)
        }
        //운동기록 블록 누르면 Add 페이지로 이동
        btn_exercise.setOnClickListener{
            val intent = Intent(this@MainActivity, ExerciseRecordActivity::class.java).apply {
                putExtra("BearerToken", BearerToken)
            }
            startActivity(intent)
        }
        setContentView(R.layout.activity_main)



        /*setSupportActionBar(findViewById(R.id.appBarLayout))*/

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (doubleBackToExitPressedOnce) {
                finish()
                return true
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "뒤로 버튼을 한 번 더 누르시면 로그아웃됩니다", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                doubleBackToExitPressedOnce = false
            }, 2000)
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onStart() {
        super.onStart()
        token = intent.getStringExtra("token")
        var retrofit: Retrofit = RetrofitClient.getInstance()
        val userservice : TokenActivity = retrofit.create(TokenActivity::class.java)
        val BearerToken = "Bearer $token"
        val callInbody = userservice.requestInBody(BearerToken)
        val callIntake = userservice.requestIntake(BearerToken, "2023-12-25")
        val callExercise = userservice.requestExercise(BearerToken, "2023-12-25")
        //인바디 조회
        callInbody.enqueue(object : Callback<ResponseInbody>{
            override fun onResponse(call: Call<ResponseInbody>, response: Response<ResponseInbody>) {
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity_callInbody", apiResponse.message)
                            var inbody = apiResponse?.data
                            summary_bmi_num.text = inbody?.bmi.toString()
                            summary_skeletalMuscle_num.text = inbody?.bmi.toString()
                            summary_fatPer_num.text = inbody?.bmi.toString()
                            some_id.text = inbody?.user?.nickName.toString() + "님"
                        }
                    }


                }
            }

            override fun onFailure(call: Call<ResponseInbody>, t: Throwable) {
                Toast.makeText(this@MainActivity, "통신 실패", Toast.LENGTH_SHORT).show()
            }

        })
        //일일 섭취량 조회
        callIntake.enqueue(object : Callback<ResponseIntake>{
            override fun onResponse(call: Call<ResponseIntake>, response: Response<ResponseIntake>
            ) {
                Log.d("MainActivity_callIntake", "여기까지됨")
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity_callIntake", apiResponse.message)
                            var intake = apiResponse?.data
                            //오늘 섭취 칼로리
                            today_intake.text = intake?.currentCalorie.toString()
                            //목표 칼로리
                            target_intake.text = intake?.targetCalorie.toString()
                        }
                        400->{
                            Log.d("MainActivity_callIntake", apiResponse.message)
                        }
                    }


                }else{
                    //통신 실패
                    try {
                        val body = response.errorBody()!!.string()
                        Log.e("Login:User", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call<ResponseIntake>, t: Throwable) {
                Log.d("MainActivity_callIntake", t.message.toString())
            }
        })
        //일일 운동 조회
        callExercise.enqueue(object : Callback<ResponseExercise>{
            override fun onResponse(call: Call<ResponseExercise>, response: Response<ResponseExercise>
            ) {
                Log.d("MainActivity_callExercise", "여기까지됨")
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity_callExercise", apiResponse.message)
                            var exercise = apiResponse?.data
                            //오늘 소모칼로리
                            today_exercise.text = exercise?.currentCalorie.toString()
                            //목표 칼로리
                            target_exercise.text = exercise?.targetCalorie.toString()
                        }
                        400->{
                            Log.d("MainActivity_callExercise", apiResponse.message)
                        }
                    }
                }else{
                    //통신 실패
                    try {
                        val body = response.errorBody()!!.string()
                        Log.e("Login:User", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            }

            override fun onFailure(call: Call<ResponseExercise>, t: Throwable) {
                Log.d("MainActivity_callExercise", t.message.toString())
            }
        })
    }

}