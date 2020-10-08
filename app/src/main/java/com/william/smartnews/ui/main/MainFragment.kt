package com.william.smartnews.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.william.smartnews.MyApplication
import com.william.smartnews.R
import com.william.smartnews.data.AppDatabase
import com.william.smartnews.feed.AdapterNewsFeed
import com.william.smartnews.feed.NewsFeedListViewModel
import com.william.smartnews.feed.NewsFeedRepository
import com.william.smartnews.models.NewsFeedItem

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: AdapterNewsFeed

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: NewsFeedListViewModel by activityViewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val repo = NewsFeedRepository(AppDatabase.getInstance(MyApplication.getInstance()).newsFeedDao())
                @Suppress("UNCHECKED_CAST")
                return NewsFeedListViewModel(repo, handle) as T
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val  view = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = view.findViewById(R.id.news_feed_list)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.setHasFixedSize(true)
        mAdapter = AdapterNewsFeed(requireContext(), emptyList())
        mAdapter.setOnItemClickListener(object : AdapterNewsFeed.OnItemClickListener {
            override fun onItemClick(view: View?, newsFeedItem: NewsFeedItem, position: Int) {
                val direction =
                    MainFragmentDirections.actionViewMainFragmentToSingleNewsArticleFragment(
                        newsFeedItem.feedId
                    )
                Navigation.findNavController(requireView()).navigate(direction)
            }
        })
        recyclerView.adapter = mAdapter
        viewModel.newsFeed.observe(viewLifecycleOwner, object : Observer<List<NewsFeedItem>?> {
            override fun onChanged(t: List<NewsFeedItem>?) {
                if (t != null) {
                    Log.d("William", "The size of the items ${t.size}")
                    mAdapter.setItems(t)
                }
            }
        })
    }

}
