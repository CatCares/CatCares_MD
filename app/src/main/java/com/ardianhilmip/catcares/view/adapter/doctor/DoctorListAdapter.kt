package com.ardianhilmip.catcares.view.adapter.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardianhilmip.catcares.R
import com.ardianhilmip.catcares.data.local.entity.DataDoctor
import com.ardianhilmip.catcares.databinding.ItemDoctorBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class DoctorListAdapter :
    PagingDataAdapter<DataDoctor, DoctorListAdapter.MyViewHolder>(DIFF_CALLBACK) {


    class MyViewHolder(private val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(doctor: DataDoctor) {
            binding.apply {
                tvName.text = doctor.nama
                tvSpesialist.text = doctor.tipe
                tvAddress.text = doctor.alamat
                Glide.with(itemView)
                    .load(doctor.foto)
                    .transition(DrawableTransitionOptions.withCrossFade())
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
        val binding = ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataDoctor>() {
            override fun areItemsTheSame(oldItem: DataDoctor, newItem: DataDoctor): Boolean {
                return oldItem.dokterId == newItem.dokterId
            }

            override fun areContentsTheSame(oldItem: DataDoctor, newItem: DataDoctor): Boolean {
                return oldItem == newItem
            }
        }
    }
}