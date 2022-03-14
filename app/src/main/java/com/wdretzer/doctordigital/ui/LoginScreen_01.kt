package com.wdretzer.doctordigital.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wdretzer.doctordigital.R
import com.wdretzer.doctordigital.viewmodel.ApiViewModel


class LoginScreen_01 : Fragment() {

    var token:String? = null

    private val viewModelLogin: ApiViewModel by viewModels()

    private val progress_bar: ProgressBar?
        get() = view?.findViewById(R.id.progress_bar)

    private val btn_login: Button?
        get() = view?.findViewById(R.id.btn_login)

    private val btn_forgot_password: TextView?
        get() = view?.findViewById(R.id.btn_forgot_password)

    private val btn_new_account: TextView?
        get() = view?.findViewById(R.id.btn_new_account)

    private val text_email: EditText?
        get() = view?.findViewById(R.id.input_email)

    private val text_password: EditText?
        get() = view?.findViewById(R.id.input_password)

    val dialogCorrect = PasswordCorrectDialogFragment()
    val dialogError = PasswordIncorrectDialogFragment()
    val dialogForgot = ForgotPasswordDialogFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_screen_01, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rendirizacoes()

        btn_login?.setOnClickListener {
            val email = text_email?.text.toString()
            val password = text_password?.text.toString()
            getLogin(email, password)
        }

        btn_forgot_password?.setOnClickListener {
            dialogForgot.show(
                parentFragmentManager,
                ForgotPasswordDialogFragment.TAG
            )
        }

        btn_new_account?.setOnClickListener {
            sendToLogin02()
        }
    }

    // Função para passar os parâmetros de Login:
    private fun getLogin(email: String, password: String) {
        viewModelLogin.login(email, password)
    }

    private fun rendirizacoes() {
        observarApi()
        observeData()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observarApi() {

        viewModelLogin.error.observe(this) {

            if (it) {
                dialogError.show(parentFragmentManager, PasswordIncorrectDialogFragment.TAG)
            }
        }

        viewModelLogin.success.observe(this) {
            token = it
            dialogCorrect.show(parentFragmentManager, PasswordCorrectDialogFragment.TAG)

            Handler().postDelayed({
                val intent = Intent(context, ProfileLoginScreen::class.java)
                //intent.putExtra("token", token)
                startActivity(intent)
            }, 200)
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observeData() {
        viewModelLogin.loading.observe(this) {
            progress_bar?.isVisible = it
        }
    }

    private fun sendToLogin02() {
        val action =
            com.wdretzer.doctordigital.ui.LoginScreen_01Directions.actionLoginScreen01ToLoginScreen02()
        findNavController().navigate(action)
    }

}
