package com.ardianhilmip.catcares.view.adapter.article

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardianhilmip.catcares.data.remote.response.article.ArticleResponse
import com.ardianhilmip.catcares.databinding.ItemArtikelVerticalBinding
import com.ardianhilmip.catcares.utils.withDateFormat
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class ArticleVerticalAdapter(private val list: ArrayList<ArticleResponse>) :
    RecyclerView.Adapter<ArticleVerticalAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemArtikelVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleResponse) {
            binding.apply {
                tvItemTitle.setText(article.judul)
                tvItemDate.setText(article.createdAt.withDateFormat())
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
        val binding = ItemArtikelVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = list[position]
        holder.bind(article)
    }

}