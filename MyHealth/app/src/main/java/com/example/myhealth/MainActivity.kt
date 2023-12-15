package com.example.myhealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import android.view.KeyEvent
import android.widget.Toast
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    var token : String? = ""

    var retrofit: Retrofit = RetrofitClient.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            Toast.makeText(this, "뒤로 버튼을 한 번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT).show()

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
    }

}