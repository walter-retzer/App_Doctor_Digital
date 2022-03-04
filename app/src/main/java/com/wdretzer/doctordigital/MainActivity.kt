package com.wdretzer.doctordigital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button


class MainActivity : AppCompatActivity() {

    private val btn_iniciar: Button
    get() = findViewById(R.id.btn_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_iniciar.setOnClickListener{
            startActivity(Intent(this, App::class.java))
            finish()
        }
    }
}
