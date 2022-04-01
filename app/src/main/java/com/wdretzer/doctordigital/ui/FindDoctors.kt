package com.wdretzer.doctordigital.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wdretzer.doctordigital.R
import com.wdretzer.doctordigital.R.color.green_olive
import com.wdretzer.doctordigital.R.color.white
import com.wdretzer.doctordigital.adapter.FindDoctorsAdapter
import com.wdretzer.doctordigital.data.DataBaseFactory
import com.wdretzer.doctordigital.model.DoctorsList
import com.wdretzer.doctordigital.model.DoctorsResponse
import com.wdretzer.doctordigital.network.DataResult
import com.wdretzer.doctordigital.viewmodel.DoctorsViewModel
import kotlinx.coroutines.delay as delay1

class FindDoctors() : AppCompatActivity() {
    var page = 1
    var limitPage = 1

    private val viewModelDoctors: DoctorsViewModel by viewModels()

    private val adapter = FindDoctorsAdapter(::saveFavourite)

    private val recycler: RecyclerView
        get() = findViewById(R.id.recycler_doctors)

    private val loading: FrameLayout
        get() = findViewById(R.id.loading)

    private val btnSendToFavorities: FloatingActionButton
        get() = findViewById(R.id.btn_fav)

    private val btnFindDoctors: FloatingActionButton
        get() = findViewById(R.id.btn_agenda)

    private val btnReturnMenu: FloatingActionButton
        get() = findViewById(R.id.btn_menu)

    private val btnReturn: androidx.appcompat.widget.Toolbar
        get() = findViewById(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_doctors)

        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter

        setScrollView()
        getList(page)

        btnSendToFavorities.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
            finish()
        }

        btnReturn.setOnClickListener {
            startActivity(Intent(this, ProfileLoginScreen::class.java))
            finish()
        }

        btnReturnMenu.setOnClickListener {
            startActivity(Intent(this, ProfileLoginScreen::class.java))
            finish()
        }
    }


    private fun saveFavourite(item: DoctorsList) {
        viewModelDoctors.addOrRemoveFavourite(item).observe(this) {
            if (it is DataResult.Success) {
                adapter.updateItem(it.data)
            }
        }
    }


    private fun getList(page: Int) {
        viewModelDoctors.listDoctors(page).observe(this, ::observeData)
    }


    private fun observeData(result: DataResult<DoctorsResponse>) {
        when (result) {
            is DataResult.Loading -> {
                loading.isVisible = result.isLoading
            }

            is DataResult.Success -> {
                val lista = result.data.doctors
                adapter.updateList(lista)
                limitPage = result.data.limitPage
            }

            is DataResult.Error -> {
                println(result.error)
                Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show()
            }

            is DataResult.Empty -> {
                Toast.makeText(this, "Result is Empty!", Toast.LENGTH_LONG).show()
            }
        }
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
                        getList(++page)
                    }
                }
            })
    }

    override fun onStart() {
        DataBaseFactory.removeInstance()
        super.onStart()
    }
}
