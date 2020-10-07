package com.william.smartnews

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class SingleNewsArticleFragment : Fragment() {

    companion object {
        fun newInstance() = SingleNewsArticleFragment()
    }

    private lateinit var viewModel: SingleNewsArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.single_news_article_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SingleNewsArticleViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
