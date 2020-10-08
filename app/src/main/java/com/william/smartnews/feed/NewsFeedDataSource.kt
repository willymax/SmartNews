package com.william.smartnews.feed

import android.util.Log
import com.google.gson.GsonBuilder
import com.william.smartnews.MyApplication
import com.william.smartnews.api.ApiModelList
import com.william.smartnews.api.ApiService
import com.william.smartnews.data.AppDatabase
import com.william.smartnews.models.NewsFeedItem
import com.william.smartnews.utils.Coroutines
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Member

/**
 * Created by Willy on 08/10/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
class NewsFeedDataSource {
    fun loadFeeds() {
        Log.d("William", "Refreshing")
        ApiService.buildInstance().loadNewsArticles().enqueue(object : Callback<ApiModelList<NewsFeedItem>?> {
            override fun onFailure(call: Call<ApiModelList<NewsFeedItem>?>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ApiModelList<NewsFeedItem>?>,
                response: Response<ApiModelList<NewsFeedItem>?>
            ) {
                if (response.isSuccessful) {
                    Log.d("William", "Refreshing successful")
                    val items = response.body()?.getData()
                    if (items != null) {
                        // val newsFeedItemType = object : TypeToken<List<NewsFeedItem>>() {}.type
                        val database = AppDatabase.getInstance(MyApplication.getInstance())
                        Coroutines.main {
                            database.newsFeedDao().insertAll(items)
                        }
                    }
                } else {
                    //loginResultsListener.onLoginResults(Error(IOException(errorMessage)))
                }
            }
        })
    }
}