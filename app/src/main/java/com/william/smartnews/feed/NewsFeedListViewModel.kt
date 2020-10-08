package com.william.smartnews.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.william.smartnews.models.NewsFeedItem

/**
 * Created by Willy on 08/10/2020
 * Email williammakau070@gmail.com
 * @author willi
 */
class NewsFeedListViewModel (
    private val repository: NewsFeedRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val newsFeed: LiveData<List<NewsFeedItem>> = repository.getNewsFeed()
    fun getNewsFeedItem(id: Long): LiveData<NewsFeedItem> {
        return repository.getNewsFeedItem(id)
    }
}