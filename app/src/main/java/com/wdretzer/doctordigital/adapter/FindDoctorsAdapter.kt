package com.wdretzer.doctordigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wdretzer.doctordigital.R
import com.wdretzer.doctordigital.model.DoctorsList


class FindDoctorsAdapter(private val action: (DoctorsList) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffUtil = AsyncListDiffer<DoctorsList>(this, DIFF_UTIL)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FindDoctorsViewHolder(
            inflater.inflate(R.layout.item_find_doctors, parent, false),
            action
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FindDoctorsViewHolder -> holder.bind(diffUtil.currentList[position])
        }
    }

    override fun getItemCount() = diffUtil.currentList.size

    fun updateList(newItens: List<DoctorsList>) {
        diffUtil.submitList(diffUtil.currentList.plus(newItens))
    }

    fun updateItem(item: DoctorsList) {
        val newList =
            diffUtil.currentList.map { doctor -> if (doctor.id == item.id) item else doctor }
        diffUtil.submitList(newList)
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<DoctorsList>() {
            override fun areItemsTheSame(oldItem: DoctorsList, newItem: DoctorsList): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DoctorsList, newItem: DoctorsList): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}


class FindDoctorsViewHolder(view: View, private val action: (DoctorsList) -> Unit) :
    RecyclerView.ViewHolder(view) {

    private val nameDoctor: TextView = view.findViewById<TextView>(R.id.name_find_doctors)
    private val imgDoctor: ImageView = view.findViewById<ImageView>(R.id.img_find_doctors)
    private val specializationDoctor: TextView =
        view.findViewById<TextView>(R.id.specialization_find_doctors)
    private val experienceDoctor: TextView =
        view.findViewById<TextView>(R.id.experience_find_doctors)
    private val classificationDoctor: TextView =
        view.findViewById<TextView>(R.id.classification_find_doctors)
    private val patientStoriesDoctor: TextView =
        view.findViewById<TextView>(R.id.pacient_stories_find_doctors)
    private val favourite: ImageButton = view.findViewById<ImageButton>(R.id.btn_fav_find_doctors)

    fun bind(item: DoctorsList) {
        nameDoctor.text = item.name
        specializationDoctor.text = item.specialization
        experienceDoctor.text = "${item.experience} Years experience"
        classificationDoctor.text = "${item.classification}%"
        patientStoriesDoctor.text = "${item.patientStories} Patient Stories"
        favourite.setImageResource(if (item.isFavourite) R.drawable.heart else R.drawable.icon_heart)
        favourite.setOnClickListener {
            action.invoke(item)
            Toast.makeText(nameDoctor.context, "Item Favoritado!", Toast.LENGTH_SHORT).show()
        }

        Glide.with(imgDoctor.context).load(item.photo).into(imgDoctor)
    }
}
