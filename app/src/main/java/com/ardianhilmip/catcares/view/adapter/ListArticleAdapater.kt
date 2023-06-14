package com.ardianhilmip.catcares.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardianhilmip.catcares.data.local.Article.Article
import com.ardianhilmip.catcares.databinding.ItemArtikelVerticalBinding

class ListArticleAdapter(private val listArticle: ArrayList<Article>) :
    RecyclerView.Adapter<ListArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(private val binding: ItemArtikelVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.apply {
                tvItemTitle.setText(article.title)
                tvItemDate.setText(article.createAt)
                ivItemPhoto.setImageResource(article.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArtikelVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = listArticle[position]
        holder.bind(article)
    }

    override fun getItemCount() = listArticle.size
}