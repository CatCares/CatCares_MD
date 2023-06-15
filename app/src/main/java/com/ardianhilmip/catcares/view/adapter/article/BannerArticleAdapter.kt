package com.ardianhilmip.catcares.view.adapter.article

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardianhilmip.catcares.data.remote.response.article.ArticleResponse
import com.ardianhilmip.catcares.databinding.ItemBannerArticleBinding
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class BannerArticleAdapter(private val list: ArrayList<ArticleResponse>) :
    RecyclerView.Adapter<BannerArticleAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemBannerArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleResponse) {
            binding.apply {
                tvItemTitle.setText(article.judul)
                Glide.with(itemView)
                    .load(article.foto)
                    .into(ivItemPhoto)
                itemView.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(article.link)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBannerArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount() = 2

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = list[position]
        holder.bind(article)
    }

}