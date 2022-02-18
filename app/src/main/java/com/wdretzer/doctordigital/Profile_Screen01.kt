package com.wdretzer.doctordigital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.wdretzer.doctordigital.profile.ProfileViewModel

class Profile_Screen01 : AppCompatActivity(R.layout.activity_profile_screen01) {

    private val viewModel: ProfileViewModel by viewModels()

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadProfile()

        observeData()
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

            name.setText(it.name.first)
            phone.setText(it.phone)
            dateBirth.setText(it.dob.date)
            localization.setText(it.location.city)

            Glide.with(this)
                .load(it.picture.large)
                .circleCrop()
                .into(profile_image)
            Toast.makeText(this, "Deu certo!", Toast.LENGTH_LONG).show()

        }

    }
}
