package com.ardianhilmip.catcares.view.adapter.cat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardianhilmip.catcares.data.local.entity.DataCat
import com.ardianhilmip.catcares.databinding.ItemCatBinding
import com.bumptech.glide.Glide

class CatListAdapter :
    PagingDataAdapter<DataCat, CatListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemCatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: DataCat) {
            binding.apply {
                txtNameCat.text = cat.nama
                Glide.with(itemView)
                    .load(cat.foto)
                    .into(imgProfile)
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
        val binding = ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataCat>() {
            override fun areItemsTheSame(oldItem: DataCat, newItem: DataCat): Boolean {
                return oldItem.kucingId == newItem.kucingId
            }

            override fun areContentsTheSame(oldItem: DataCat, newItem: DataCat): Boolean {
                return oldItem == newItem
            }
        }
    }

}