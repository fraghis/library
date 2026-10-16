package com.hawkins.library

import android.app.Application

class HawkinsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: HawkinsApp
            private set
    }
}
