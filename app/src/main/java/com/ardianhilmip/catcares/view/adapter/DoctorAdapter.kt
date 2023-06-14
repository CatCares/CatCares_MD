package com.ardianhilmip.catcares.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardianhilmip.catcares.data.local.Doctor.Doctor
import com.ardianhilmip.catcares.databinding.ItemDoctorBinding


class DoctorAdapter(private val listDoctor: ArrayList<Doctor>) :
    RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    class DoctorViewHolder(private val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(doctor: Doctor) {
            binding.apply {
                tvName.setText(doctor.name)
                tvSpesialist.setText(doctor.specialist)
                imgProfile.setImageResource(doctor.photo)
                tvAddress.setText(doctor.location)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val binding = ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = listDoctor[position]
        holder.bind(doctor)
    }

    override fun getItemCount() = listDoctor.size
}