package com.wdretzer.doctordigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wdretzer.doctordigital.R
import com.wdretzer.doctordigital.model.DoctorsList

class FavouriteDoctorsAdapter(private val favoritos: MutableList<DoctorsList>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FavouriteViewHolder(
            inflater.inflate(R.layout.item_doctor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavouriteViewHolder -> holder.bind(favoritos[position])
        }
    }

    override fun getItemCount(): Int = favoritos.size
}

class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val nameDoctorFav: TextView = view.findViewById(R.id.doctor_name_fav)
    private val imgDoctor: ImageView = view.findViewById(R.id.imgem_doctor_fav)
    private val specializationDoctor: TextView =
        view.findViewById(R.id.doctor_speciality_fav)
    private val expirienceDoctor: TextView = view.findViewById<TextView>(R.id.doctor_expirience_fav)
    private val classificationDoctor: TextView = view.findViewById<TextView>(R.id.porcentagem_fav)
    private val patientStoriesDoctor: TextView = view.findViewById<TextView>(R.id.pacient_stories_fav)
    private val favourite: ImageButton = view.findViewById(R.id.favorito_img_fav)

    fun bind(itensFav: DoctorsList) {
        nameDoctorFav.text = itensFav.name
        specializationDoctor.text = itensFav.specialization
        expirienceDoctor.text = "${itensFav.experience} Years expirience"
        classificationDoctor.text = "${itensFav.classification}%"
        patientStoriesDoctor.text = "${itensFav.patientStories} Patient Stories"
        Glide.with(imgDoctor.context).load(itensFav.photo).into(imgDoctor)
        favourite.setImageResource(if (itensFav.isFavourite) R.drawable.icon_heart else R.drawable.heart)
    }
}
