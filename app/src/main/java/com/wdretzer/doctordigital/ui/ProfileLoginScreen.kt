package com.wdretzer.doctordigital.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.wdretzer.doctordigital.R
import com.wdretzer.doctordigital.viewmodel.ProfileLoginViewModel


class ProfileLoginScreen : AppCompatActivity(R.layout.activity_profile_login_screen) {

    private val viewModel: ProfileLoginViewModel by viewModels()

    private val loading: FrameLayout
        get() = findViewById(R.id.loading)

    private val name: EditText
        get() = findViewById(R.id.input_name)

    private val phone: EditText
        get() = findViewById(R.id.input_phone)

    private val dateBirth: EditText
        get() = findViewById(R.id.input_day_birth)

    private val localization: EditText
        get() = findViewById(R.id.input_localization)

    private val profile_image: ImageView
        get() = findViewById(R.id.profile_imagem)

    private val btn_continue: Button
        get() = findViewById(R.id.btn_onboarding)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadProfile()

        observeData()

        btn_continue.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun observeData() {
        viewModel.loading.observe(this) {
            loading.isVisible = it
        }

        viewModel.error.observe(this) {
            if (it) {
                Toast.makeText(this, "Deu erro", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.profile.observe(this) {

            Glide.with(this)
                .load("${it.results.first().picture.large}")
                .placeholder(R.drawable.icon_acount)
                .error(R.drawable.icon_error)
                .circleCrop()
                .into(profile_image)

            name.setText(it.results.first().name.first + " " + it.results.first().name.last)
            phone.setText(it.results.first().phone)
            dateBirth.setText(it.results.first().dob.date)
            localization.setText(it.results.first().location.city)

            Toast.makeText(this, "Deu certo!", Toast.LENGTH_LONG).show()

        }
    }
}
