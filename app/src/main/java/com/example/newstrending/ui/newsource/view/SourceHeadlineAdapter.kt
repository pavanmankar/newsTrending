package com.example.newstrending.ui.newsource.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newstrending.data.model.Article
import com.example.newstrending.data.model.NewSource
import com.example.newstrending.databinding.TopHeadlineItemLayoutBinding
import com.example.newstrending.ui.topheadline.view.TopHeadlineAdapter

class SourceHeadlineAdapter(
    private val articleList: ArrayList<NewSource>
) : RecyclerView.Adapter<SourceHeadlineAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: NewSource) {
            binding.textViewTitle.text = article.name
            binding.textViewDescription.text = article.description

            Glide.with(binding.imageViewBanner.context)
                .load(article.url)
                .into(binding.imageViewBanner)

            itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, Uri.parse(article.url))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            TopHeadlineItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(articleList[position])

    fun addData(list: List<NewSource>) {
        articleList.addAll(list)
    }

}