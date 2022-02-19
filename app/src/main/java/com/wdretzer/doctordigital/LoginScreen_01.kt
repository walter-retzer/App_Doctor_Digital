package com.wdretzer.doctordigital

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wdretzer.doctordigital.network.DataResult
import com.wdretzer.doctordigital.viewmodel.LoginViewModel


class LoginScreen_01 : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private val progress_bar: ProgressBar?
        get() = view?.findViewById(R.id.progress_bar)

    private val btn_login: Button?
        get() = view?.findViewById(R.id.btn_login)

    private val btn_forgot_password: TextView?
        get() = view?.findViewById(R.id.btn_forgot_password)

    private val btn_new_account: TextView?
        get() = view?.findViewById(R.id.btn_new_account)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_screen_01, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = ForgotPasswordDialogFragment()

        progress_bar?.isVisible = false

        btn_login?.setOnClickListener {
            login("teste@teste.com", "password")
        }

        btn_forgot_password?.setOnClickListener {
            dialog.show(
                parentFragmentManager,
                ForgotPasswordDialogFragment.TAG
            )
        }

        btn_new_account?.setOnClickListener {
            sendToLogin02()
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun login(email: String, password: String) {

        val dialog3 = PasswordCorrectDialogFragment()
        val dialog4 = PasswordIncorrectDialogFragment()

        viewModel.login(email, password).observe(this) {
            when (it) {
                is DataResult.Loading -> {
                    showLoading(true)
                }
                is DataResult.Success -> {
                    showLoading(false)
                    //Toast.makeText(context, "Fez Login -> ${it.data}", Toast.LENGTH_LONG).show()
                    dialog3.show(parentFragmentManager, PasswordCorrectDialogFragment.TAG)
                }
                is DataResult.Error -> {
                    showLoading(false)
                    //Toast.makeText(context, "Deu Erro", Toast.LENGTH_LONG).show()
                    dialog4.show(parentFragmentManager, PasswordIncorrectDialogFragment.TAG)
                }
            }
        }
    }

    private fun showLoading(isInProgress: Boolean) {
        progress_bar?.isVisible = isInProgress
    }

    private fun sendToLogin02() {
        val action =
            LoginScreen_01Directions.actionLoginScreen01ToLoginScreen02()
        findNavController().navigate(action)
    }

}
