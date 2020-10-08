package com.william.smartnews.feed

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.william.smartnews.models.NewsFeedItem

/**
 * Created by Willy on 08/10/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
/**
 * The Data Access Object for the NewsFeed class.
 */
@Dao
interface NewsFeedDao {
    @Query("SELECT * FROM newsfeeds")
    fun getNewsFeed(): LiveData<List<NewsFeedItem>>

    @Query("SELECT * FROM newsfeeds WHERE id = :feedId")
    fun getNewsFeedItem(feedId: Long): LiveData<NewsFeedItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newsFeeds: List<NewsFeedItem>)
}