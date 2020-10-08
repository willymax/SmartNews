package com.william.smartnews.utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

/**
 * Created by Willy on 07/10/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
class NetworkUtils {
    companion object {
        fun buildClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        // Get the request from the chain.
                        val original = chain.request()

                        // Get url
                        val url = original.url.newBuilder()
                        // Add common params to the url
                        //url.addQueryParameter("", "");
                        val request = original.newBuilder()
                            .url(url.build())
                            .method(original.method, original.body)
                            .header("Accept", "application/json")
                        //                                .header("Authorization", "Bearer ${mCallback.getAuthToken()}");

                        // Add the modified request to the chain.
                        val response = chain.proceed(request.build())


                        //unauthenticated or unauthorized
                        if (response.code == 401 || response.code == 403) {
                            //
                        }
                        return response
                    }
                })
            val logger = HttpLoggingInterceptor()
            logger.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(logger)
            return builder.build()
        }
    }
}