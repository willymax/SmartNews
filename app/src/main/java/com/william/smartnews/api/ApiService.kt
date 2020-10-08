package com.william.smartnews.api

import com.william.smartnews.models.Feed
import com.william.smartnews.utils.BASE_URL
import com.william.smartnews.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

/**
 * Created by Willy on 07/10/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
interface ApiService {
    @GET("feed")
    fun loadNewsArticles(): Call<ApiModelList<Feed?>>

    companion object {
        /**
         * Build an instance of the app Api service
         * @return ApiService
         */
        fun buildInstance(): ApiService? {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(NetworkUtils.buildClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}