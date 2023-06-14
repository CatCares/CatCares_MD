package com.ardianhilmip.catcares.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardianhilmip.catcares.data.local.entity.ArticleDataItem
import com.ardianhilmip.catcares.databinding.ItemArtikelVerticalBinding

class ArticleListAdapter :
    PagingDataAdapter<ArticleDataItem, ArticleListAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    class ArticleViewHolder(private val binding: ItemArtikelVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleDataItem) {
            binding.apply {
                tvItemTitle.text = article.judul
                tvItemDate.text = article.createdAt
            }
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArtikelVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleDataItem>() {
            override fun areItemsTheSame(oldItem: ArticleDataItem, newItem: ArticleDataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleDataItem, newItem:ArticleDataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}