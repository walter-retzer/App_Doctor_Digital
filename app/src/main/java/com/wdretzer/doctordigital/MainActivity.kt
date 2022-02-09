package com.wdretzer.doctordigital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val btnDialog: Button
    get() = findViewById(R.id.btn_main)

    private val btnDialog2: Button
        get() = findViewById(R.id.btn_main2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getSupportActionBar()?.hide()

        val dialog = ForgotPasswordDialogFragment()
        btnDialog.setOnClickListener{
            dialog.show(supportFragmentManager, ForgotPasswordDialogFragment.TAG)
        }

        val dialog2 = ResetPasswordDialogFragment()
        btnDialog2.setOnClickListener{
            dialog2.show(supportFragmentManager, ResetPasswordDialogFragment.TAG)
        }
    }
}