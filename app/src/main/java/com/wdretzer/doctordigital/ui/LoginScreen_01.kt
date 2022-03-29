package com.wdretzer.doctordigital.ui

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
import com.wdretzer.doctordigital.extension.SessionManager
import com.wdretzer.doctordigital.network.DataResult
import com.wdretzer.doctordigital.viewmodel.ApiViewModel


class LoginScreen_01 : Fragment() {

    var token: String? = null

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

        btn_login?.setOnClickListener {
            loadUser()
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

    private fun loadUser() {
        viewModelLogin.login(text_email?.text.toString(), text_password?.text.toString())
            .observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Loading -> {
                        progress_bar?.isVisible = it.isLoading
                        btn_login?.isVisible = it.isLoading.not()
                    }

                    is DataResult.Error -> {
                        dialogError.show(parentFragmentManager, PasswordIncorrectDialogFragment.TAG)
                    }
                    is DataResult.Empty -> {
                        Toast.makeText(context, "Erro ao Receber os Dados!", Toast.LENGTH_LONG).show()
                    }

                    is DataResult.Success -> {
                        dialogCorrect.show(parentFragmentManager, PasswordCorrectDialogFragment.TAG)
                        token = it.data.token

                        SessionManager.saveSession(it.data.token)
                        SessionManager.saveProfile(it.data.user)

                        Handler().postDelayed({
                            val intent = Intent(context, ProfileLoginScreen::class.java)
                            startActivity(intent)
                        }, 2000)
                    }
                }
            }
    }

    private fun sendToLogin02() {
        val action =
            com.wdretzer.doctordigital.ui.LoginScreen_01Directions.actionLoginScreen01ToLoginScreen02()
        findNavController().navigate(action)
    }

}
