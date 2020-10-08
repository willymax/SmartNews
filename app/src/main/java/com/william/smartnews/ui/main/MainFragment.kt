package com.william.smartnews.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.william.smartnews.MyApplication
import com.william.smartnews.R
import com.william.smartnews.data.AppDatabase
import com.william.smartnews.feed.AdapterNewsFeed
import com.william.smartnews.feed.NewsFeedDataSource
import com.william.smartnews.feed.NewsFeedListViewModel
import com.william.smartnews.feed.NewsFeedRepository
import com.william.smartnews.models.NewsFeedItem

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: AdapterNewsFeed
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

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
                val repo = NewsFeedRepository(AppDatabase.getInstance(MyApplication.getInstance()).newsFeedDao(), NewsFeedDataSource())
                @Suppress("UNCHECKED_CAST")
                return NewsFeedListViewModel(repo, handle) as T
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val  view = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = view.findViewById(R.id.news_feed_list)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadNewsFeed()
        }
        viewModel.loadNewsFeed()
        swipeRefreshLayout.isRefreshing = true
        Snackbar.make(view, getString(R.string.loading), Snackbar.LENGTH_SHORT).show()
        recyclerView.setHasFixedSize(true)
        mAdapter = AdapterNewsFeed(requireContext(), emptyList())
        mAdapter.setOnItemClickListener(object : AdapterNewsFeed.OnItemClickListener {
            override fun onItemClick(view: View?, newsFeedItem: NewsFeedItem, position: Int) {
                val direction =
                    MainFragmentDirections.actionViewMainFragmentToSingleNewsArticleFragment(
                        newsFeedItem.feedId
                    )
                val bundle = bundleOf("feedId" to newsFeedItem.feedId)
                Navigation.findNavController(requireView()).navigate(R.id.action_view_main_fragment_to_single_news_article_fragment, bundle)
            }
        })
        recyclerView.adapter = mAdapter
        viewModel.newsFeed.observe(viewLifecycleOwner, object : Observer<List<NewsFeedItem>?> {
            override fun onChanged(t: List<NewsFeedItem>?) {
                swipeRefreshLayout.isRefreshing = false
                if (t != null) {
                    Log.d("William", "The size of the items ${t.size}")
                    mAdapter.setItems(t)
                }
            }
        })
    }

}
