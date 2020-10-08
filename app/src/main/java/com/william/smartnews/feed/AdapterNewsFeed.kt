package com.william.smartnews.feed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.william.smartnews.R
import com.william.smartnews.models.NewsFeedItem
import com.william.smartnews.utils.Tools

/**
 * Created by Willy on 08/10/2020
 * Email williammakau070@gmail.com
 * @author willi
 */

class AdapterNewsFeed(
    private val context: Context,
    private var items: List<NewsFeedItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ctx: Context? = null


    var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View?, obj: NewsFeedItem, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener?) {
        this.mOnItemClickListener = mItemClickListener
    }


    class OriginalViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var image: ImageView = v.findViewById(R.id.image)
        var title: TextView = v.findViewById(R.id.title)
        var feedDescription: TextView = v.findViewById(R.id.feedDescription)
        var lytParent: View = v.findViewById(R.id.lyt_parent)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_news_feed, parent, false)
        vh = OriginalViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OriginalViewHolder) {
            val newsFeedItem: NewsFeedItem = items[position]
            holder.title.text = newsFeedItem.feedTitle
            holder.feedDescription.text = newsFeedItem.feedDescription
            Tools.displayImageOriginal(context, holder.image, newsFeedItem.feedImage)
            holder.lytParent.setOnClickListener(View.OnClickListener { view ->
                if (mOnItemClickListener == null) return@OnClickListener
                mOnItemClickListener?.onItemClick(view, items[position], position)
            })
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return items[position].feedId
    }

    fun setItems(t: List<NewsFeedItem>) {
        this.items = t
        notifyDataSetChanged()
    }
}