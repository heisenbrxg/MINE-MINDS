package com.tl.mineminds

import android.app.Application
import okhttp3.OkHttpClient

class MineMinds: Application() {

    companion object {
        lateinit var httpClient: OkHttpClient
        lateinit var instance: MineMinds
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        httpClient = OkHttpClient()
    }

}