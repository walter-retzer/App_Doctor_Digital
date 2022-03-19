package com.wdretzer.doctordigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wdretzer.doctordigital.R
import com.wdretzer.doctordigital.model.DoctorsList

class FindDoctorsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffUtil = AsyncListDiffer<DoctorsList>(this, DIFF_UTIL)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FindDostorsViewHolder(inflater.inflate(R.layout.item_doctor_list, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FindDostorsViewHolder -> holder.bind(diffUtil.currentList[position])
        }
    }

    override fun getItemCount() = diffUtil.currentList.size

    fun updateList(newItens: List<DoctorsList>) {
        diffUtil.submitList(diffUtil.currentList.plus(newItens))
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<DoctorsList>() {
            override fun areItemsTheSame(oldItem: DoctorsList, newItem: DoctorsList): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DoctorsList, newItem: DoctorsList): Boolean {
                return oldItem == newItem
            }

        }
    }


}

class FindDostorsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val nameDoctor: TextView = view.findViewById<TextView>(R.id.doctor_name)
    private val imgDoctor: ImageView = view.findViewById<ImageView>(R.id.imgem_doctor)
    private val specializationDoctor: TextView =
        view.findViewById<TextView>(R.id.doctor_speciality)
    private val classificationDoctor: TextView = view.findViewById<TextView>(R.id.text_item)
    private val viewsDoctor: TextView = view.findViewById<TextView>(R.id.text_views)

    fun bind(item: DoctorsList) {

        nameDoctor.text = item.name
        specializationDoctor.text = item.specialization
        classificationDoctor.text = item.classification.toString()
        viewsDoctor.text = ("${item.views} views")

        Glide.with(imgDoctor.context).load(item.photo).into(imgDoctor)
    }
}
