package com.william.smartnews

import android.app.Application

/**
 * Created by Willy on 08/10/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object {
        private lateinit var mInstance: MyApplication
        @Synchronized
        fun getInstance(): MyApplication {
            return mInstance
        }
    }
}