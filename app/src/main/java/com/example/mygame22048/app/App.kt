package com.example.mygame22048.app

import android.app.Application
import com.example.mygame22048.domain.MySharedPref

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        MySharedPref.init(this)
    }
}