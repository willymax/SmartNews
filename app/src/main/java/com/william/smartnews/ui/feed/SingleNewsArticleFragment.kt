package com.william.smartnews.ui.feed


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.william.smartnews.MainActivity
import com.william.smartnews.MyApplication
import com.william.smartnews.R
import com.william.smartnews.data.AppDatabase
import com.william.smartnews.feed.NewsFeedDataSource
import com.william.smartnews.feed.NewsFeedListViewModel
import com.william.smartnews.feed.NewsFeedRepository
import com.william.smartnews.models.NewsFeedItem
import com.william.smartnews.utils.Tools
import kotlinx.android.synthetic.main.main_activity.*


class SingleNewsArticleFragment : Fragment() {
    private lateinit var feedTitle: TextView
    private lateinit var feedImage: ImageView
    private lateinit var feedDescription: TextView
    private var theArticle: NewsFeedItem? = null
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

    companion object {
        fun newInstance() =
            SingleNewsArticleFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val  view = inflater.inflate(R.layout.single_news_article_fragment, container, false)
        feedTitle = view.findViewById(R.id.feedTitle)
        feedImage = view.findViewById(R.id.feedImage)
        feedDescription = view.findViewById(R.id.feedDescription)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getLong("feedId")?.let {
            viewModel.getNewsFeedItem(it).observe(viewLifecycleOwner, object : Observer<NewsFeedItem?> {
                override fun onChanged(feedItem: NewsFeedItem?) {
                    theArticle = feedItem
                    if (feedItem != null) {
                        feedTitle.text = feedItem.feedTitle
                        feedDescription.text = feedItem.feedDescription
                        Tools.displayImageOriginal(requireContext(), feedImage, feedItem.feedImage)
                    }
                }
            })
        }
        (activity as MainActivity).topAppBar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                return when (item?.itemId) {
                    R.id.action_share -> {
                        share()
                        true
                    }
                    else -> false
                }
            }
        })
    }
    fun share() {
        theArticle?.let {article ->
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, article.feedTitle)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}
