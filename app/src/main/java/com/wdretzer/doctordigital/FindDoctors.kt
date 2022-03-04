package com.wdretzer.doctordigital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.wdretzer.doctordigital.viewmodel.DoctorsViewModel


class FindDoctors : AppCompatActivity() {

    private val viewModelDoctors: DoctorsViewModel by viewModels()

    private val listaDoctors: TextView?
        get() = findViewById(R.id.list_doctors)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_doctors)

        val bundle: Bundle? = intent.extras

        if (bundle != null) {
            var token = bundle.getString("token").toString()
            getLogin(token)
            Toast.makeText(this, token, Toast.LENGTH_LONG).show()
        }

        observarApi()

    }

    private fun observarApi() {

        viewModelDoctors.error.observe(this) {
            if (it) {
                listaDoctors?.text = "Falha -> ${it.toString()}"
                Toast.makeText(this, "Falha!!", Toast.LENGTH_LONG).show()
            }
        }

        viewModelDoctors.success.observe(this) {

            listaDoctors?.text = "Lista: -> ${it}"

        }
    }

    // Função para passar os parâmetros de Login:
    private fun getLogin(token: String) {
        viewModelDoctors.listDoctors(token)
    }
}
