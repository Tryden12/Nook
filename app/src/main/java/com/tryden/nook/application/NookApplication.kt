package com.tryden.nook.application

import android.app.Application
import android.content.Context

class NookApplication: Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}