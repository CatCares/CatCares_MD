package com.ardianhilmip.catcares.view.adapter.article

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardianhilmip.catcares.data.local.entity.DataItem
import com.ardianhilmip.catcares.databinding.ItemBannerArticleBinding
import com.bumptech.glide.Glide

class BannerArticleAdapter :
    PagingDataAdapter<DataItem, BannerArticleAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemBannerArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: DataItem) {
            binding.apply {
                tvItemTitle.text = article.judul
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

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemBannerArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.artikelId == newItem.artikelId
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}