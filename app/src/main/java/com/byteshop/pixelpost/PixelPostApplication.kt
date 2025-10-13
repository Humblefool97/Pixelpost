package com.byteshop.pixelpost

import android.app.Application
import com.google.firebase.FirebaseApp

class PixelPostApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}