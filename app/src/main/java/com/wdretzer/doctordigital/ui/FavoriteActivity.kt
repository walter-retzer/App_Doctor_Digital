package com.wdretzer.doctordigital.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wdretzer.doctordigital.R
import com.wdretzer.doctordigital.adapter.FavouriteDoctorsAdapter
import com.wdretzer.doctordigital.viewmodel.DoctorsViewModel


class FavoriteActivity : AppCompatActivity() {

    private val viewModelDoctorsFav: DoctorsViewModel by viewModels()

    private val recycler: RecyclerView
        get() = findViewById(R.id.recycler_doctors2)

    private val btnReturn: androidx.appcompat.widget.Toolbar
        get() = findViewById(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favority)

        showFavourite()

        btnReturn.setOnClickListener {
            startActivity(Intent(this, FindDoctors::class.java))
            finish()
        }
    }

    private fun showFavourite() {
        viewModelDoctorsFav.getFavourite().observe(this) {
            val adapter = FavouriteDoctorsAdapter(it)
            recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recycler.adapter = adapter
        }
    }
}
