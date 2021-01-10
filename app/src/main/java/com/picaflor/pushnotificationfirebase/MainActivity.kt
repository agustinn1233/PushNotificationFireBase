package com.picaflor.pushnotificationfirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //dynamicInstance()

    }

    // dynamic instance without google-service.json
    private fun dynamicInstance() {
        FirebaseApp.initializeApp(
            applicationContext,
            FirebaseOptions.Builder().setApiKey("AIzaSyDmsTrSrTyAYm9XKpq33euq9_JeLUqobZk").setApplicationId("1:731945957773:android:6f519da7490494e054a5c4")
                .setGcmSenderId("731945957773").setProjectId("pushnotifications-aa527").setStorageBucket("pushnotifications-aa527.appspot.com").build()
        )
    }
}