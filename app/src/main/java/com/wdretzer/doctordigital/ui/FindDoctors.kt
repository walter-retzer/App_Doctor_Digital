package com.wdretzer.doctordigital.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wdretzer.doctordigital.R
import com.wdretzer.doctordigital.adapter.FindDoctorsAdapter
import com.wdretzer.doctordigital.data.entity.DoctorEntry.COLUMN_NAME_API_ID
import com.wdretzer.doctordigital.data.entity.DoctorEntry.COLUMN_NAME_CLASSIFICATION
import com.wdretzer.doctordigital.data.entity.DoctorEntry.COLUMN_NAME_EXPERIENCE
import com.wdretzer.doctordigital.data.entity.DoctorEntry.COLUMN_NAME_NAME
import com.wdretzer.doctordigital.data.entity.DoctorEntry.COLUMN_NAME_PATIENT_STORIES
import com.wdretzer.doctordigital.data.entity.DoctorEntry.COLUMN_NAME_PHOTO
import com.wdretzer.doctordigital.data.entity.DoctorEntry.COLUMN_NAME_SPECIALIZATION
import com.wdretzer.doctordigital.data.entity.DoctorEntry.COLUMN_NAME_VIEWS
import com.wdretzer.doctordigital.data.local.DbDoctorHelper
import com.wdretzer.doctordigital.data.local.DbHelper
import com.wdretzer.doctordigital.viewmodel.DoctorsViewModel

class FindDoctors() : AppCompatActivity() {

    private val viewModelDoctors: DoctorsViewModel by viewModels()
    var page = 1
    var limitPage = 1
    private val adapter = FindDoctorsAdapter()
    private val recycler: RecyclerView
        get() = findViewById(R.id.recycler_doctors)
    private val loading: FrameLayout
        get() = findViewById(R.id.loading)
    private val listaDoctors: TextView?
        get() = findViewById(R.id.list_doctors)
    private val btnSave: FloatingActionButton
    get() = findViewById(R.id.btn_save)


    private lateinit var dbDoctorsHelper: DbDoctorHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_doctors)

        dbDoctorsHelper = DbDoctorHelper(this)

        btnSave.setOnClickListener{
            save()
        }

        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter
        setScrollView()

        observarApi()
        updateList(1)
    }

    private fun save() {
        val id = dbDoctorsHelper.insert {
            put(COLUMN_NAME_NAME, "Abacate")
            put(COLUMN_NAME_PHOTO, "Foto")
            put(COLUMN_NAME_SPECIALIZATION, "")
            put(COLUMN_NAME_CLASSIFICATION, "")
            put(COLUMN_NAME_EXPERIENCE, "")
            put(COLUMN_NAME_PATIENT_STORIES, "")
            put(COLUMN_NAME_VIEWS, "")
            put(COLUMN_NAME_API_ID, "")
        }
        println(id)
        listWithAbacate()
        deleteDoctor(id)
    }

    private fun deleteDoctor(id: Long) {
    dbDoctorsHelper.delete(id)
    }

    private fun listWithAbacate() {
       dbDoctorsHelper.listAllWithAbacate()
    }

    private fun observarApi() {

        viewModelDoctors.loading.observe(this) {
            loading.isVisible = it
        }

        viewModelDoctors.error.observe(this) {
            if (it) {
                listaDoctors?.text = "Falha -> ${it.toString()}"
                Toast.makeText(this, "Falha!!", Toast.LENGTH_LONG).show()
            }
        }

        viewModelDoctors.success.observe(this) {
            adapter.updateList(it.doctors)
            limitPage = it.limitPage
        }
    }

    // Função para passar os parâmetros do valor da página da API:
    private fun updateList(pageItem: Int) {
        viewModelDoctors.listDoctors(pageItem)
    }

    private fun setScrollView() {
        recycler
            .addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val target = recyclerView.layoutManager as LinearLayoutManager
                    val totalCountItens = target.itemCount
                    val lastItemVisible = target.findLastVisibleItemPosition()

                    val lastItem = (lastItemVisible + 5 >= totalCountItens)

                    if ((totalCountItens > 0 && lastItem) && (page < limitPage && loading.isVisible.not())) {
                        updateList(++page)
                    }
                }
            })
    }
}
