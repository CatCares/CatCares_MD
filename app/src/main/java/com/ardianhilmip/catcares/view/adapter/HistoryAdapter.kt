package com.ardianhilmip.catcares.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardianhilmip.catcares.data.local.History.History
import com.ardianhilmip.catcares.databinding.ItemHistoryBinding

class HistoryAdapter(private val listHistory: ArrayList<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            binding.apply {
                imgProfile.setImageResource(history.photo)
                tvName.setText(history.name)
                tvSpesialis.setText(history.specialist)
                imgProfile.setImageResource(history.photo)
                tvDate.setText(history.date)
                tvTime.setText(history.time)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = listHistory[position]
        holder.bind(history)
    }

    override fun getItemCount() = listHistory.size

}
