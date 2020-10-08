package com.william.smartnews.feed

/**
 * Created by Willy on 08/10/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
class NewsFeedRepository(private val newsFeedDao: NewsFeedDao) {
    fun getNewsFeed() = newsFeedDao.getNewsFeed()

    fun getNewsFeedItem(feedId: Long) = newsFeedDao.getNewsFeedItem(feedId)

}