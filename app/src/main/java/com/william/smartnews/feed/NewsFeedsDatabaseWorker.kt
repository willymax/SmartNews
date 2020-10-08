package com.william.smartnews.feed

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.william.smartnews.api.ApiService
import com.william.smartnews.data.AppDatabase
import kotlinx.coroutines.coroutineScope

/**
 * Created by Willy on 08/10/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
class NewsFeedsDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            val args = inputData
            val response = ApiService.buildInstance().loadNewsArticles().execute()
            // Check to see if the get request succeeded.
            if (!response.isSuccessful) {
                val errorBody = response.errorBody()
                val error = errorBody?.string()
                Log.e(TAG, "Error seeding database $error")
                Result.retry()
            } else {
                Log.e(TAG, "Request successful")
                val optionsResponse = response.body()
                if (optionsResponse != null) {
                    // val newsFeedItemType = object : TypeToken<List<NewsFeedItem>>() {}.type
                    val database = AppDatabase.getInstance(applicationContext)
                    database.newsFeedDao().insertAll(optionsResponse.getData())
                }
                Result.success()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error seeding database", e)
            Result.retry()
            //Result.failure()
        }
    }

    companion object {
        private const val TAG = "NewsFeedsDatabaseWorker"
    }
}