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
import com.wdretzer.doctordigital.R
import com.wdretzer.doctordigital.adapter.FindDoctorsAdapter
import com.wdretzer.doctordigital.viewmodel.DoctorsViewModel

class FindDoctors() : AppCompatActivity() {

    private val viewModelDoctors: DoctorsViewModel by viewModels()

    private val adapter = FindDoctorsAdapter()
    private val recycler: RecyclerView
        get() = findViewById(R.id.recycler_doctors)

    private val loading: FrameLayout
        get() = findViewById(R.id.loading)

    private val listaDoctors: TextView?
        get() = findViewById(R.id.list_doctors)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_doctors)

        getList(0)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        observarApi()
        recycler.adapter = adapter
    }

    private fun observarApi() {

        viewModelDoctors.loading.observe(this) {
            loading?.isVisible = it
        }

        viewModelDoctors.error.observe(this) {
            if (it) {
                listaDoctors?.text = "Falha -> ${it.toString()}"
                Toast.makeText(this, "Falha!!", Toast.LENGTH_LONG).show()
            }
        }

        viewModelDoctors.success.observe(this) {
            adapter.updateList(it.doctors)
        }
    }

    // Função para passar os parâmetros do valor da página da API:
    private fun getList(pageItem: Int) {
        viewModelDoctors.listDoctors(pageItem)
    }

    private fun setScrollView() {
        val page = 1
        val limitePage = 4
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val target = recyclerView.layoutManager as LinearLayoutManager
                val totalCountItens = target.itemCount
                val lastItemVisible = target.findLastVisibleItemPosition()

                val lastItem = lastItemVisible + 5 >= totalCountItens

                if ((totalCountItens > 0 && lastItem) && page <= limitePage) {

                }

            }
        })
    }
}
