package com.example.myhealth

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
        val callIntake = userservice.requestIntake(BearerToken,"2023-12-16")

        //인바디 조회
        callInbody.enqueue(object : Callback<ResponseInbody>{
            override fun onResponse(call: Call<ResponseInbody>, response: Response<ResponseInbody>) {
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity", apiResponse.message)
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
                if(response.isSuccessful){
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity", apiResponse.message)
                            var intake = apiResponse?.data
                            //오늘 섭취 칼로리
                            today_intake.text = intake?.currentCalorie.toString()
                            //목표 칼로리
                            target_intake.text = intake?.targetCalorie.toString()
                        }
                    }


                }
            }

            override fun onFailure(call: Call<ResponseIntake>, t: Throwable) {
            }
        })
        //일일 운동 조회



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
        val call = userservice.requestInBody(BearerToken)

        /*인바디 조회*/
        call.enqueue(object : Callback<ResponseInbody>{
            override fun onResponse(call: Call<ResponseInbody>, response: Response<ResponseInbody>) {
                Log.d("MainActivity", "onResponse")
                Log.d("MainActivity", response.code().toString())
                if(response.isSuccessful){
                    Log.d("MainActivity", "isSuccessful")
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity", apiResponse.message)
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
                Log.d("MainActivity", "onFailure")
                Toast.makeText(this@MainActivity, "통신 실패", Toast.LENGTH_SHORT).show()
            }

        })

        /*섭취 조회*/
        call.enqueue(object : Callback<ResponseInbody>{
            override fun onResponse(call: Call<ResponseInbody>, response: Response<ResponseInbody>) {
                Log.d("MainActivity", "onResponse")
                Log.d("MainActivity", response.code().toString())
                if(response.isSuccessful){
                    Log.d("MainActivity", "isSuccessful")
                    var apiResponse = response.body()
                    when(apiResponse?.code){
                        200->{
                            Log.d("MainActivity", apiResponse.message)
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
                Log.d("MainActivity", "onFailure")
                Toast.makeText(this@MainActivity, "통신 실패", Toast.LENGTH_SHORT).show()
            }

        })
        /*운동 조회*/
    }

}