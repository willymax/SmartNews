package com.william.smartnews.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Willy on 07/10/2020
 * Email williammakau070@gmail.com
 * @author willi
 * This class models a single news articles
 */
@Entity(tableName = "newsfeeds")
class NewsFeedItem(
    @PrimaryKey @ColumnInfo(name = "id") val feedId: Long,
    val feedTitle: String,
    val feedDescription: String,
    val feedImage: String
)