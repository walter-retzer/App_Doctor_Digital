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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.wdretzer.doctordigital.R
import com.wdretzer.doctordigital.network.DataResult
import com.wdretzer.doctordigital.util.GoogleLogInActivityContract
import com.wdretzer.doctordigital.viewmodel.ApiViewModel


class LoginScreen_01 : Fragment() {

    private val googleSignInRequest = registerForActivityResult(
        GoogleLogInActivityContract(),
        ::onGoogleSigInResult
    )

    private val googleSignInOptions: GoogleSignInOptions
    get() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.google_id))
        .requestEmail()
        .requestProfile()
        .build()

    var token: String? = null

    private val viewModelLogin: ApiViewModel by viewModels()

    private val progressBar: ProgressBar?
        get() = view?.findViewById(R.id.progress_bar)

    private val btnLogin: Button?
        get() = view?.findViewById(R.id.btn_login)

    private val btnGoogle: Button?
        get() = view?.findViewById(R.id.btn_google)

    private val btnForgotPassword: TextView?
        get() = view?.findViewById(R.id.btn_forgot_password)

    private val btnNewAccount: TextView?
        get() = view?.findViewById(R.id.btn_new_account)

    private val textEmail: EditText?
        get() = view?.findViewById(R.id.input_email)

    private val textPassword: EditText?
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

        btnGoogle?.setOnClickListener {
            googleSignInRequest.launch(googleSignInOptions)
        }

        btnLogin?.setOnClickListener {
            loadUser()
        }

        btnForgotPassword?.setOnClickListener {
            dialogForgot.show(
                parentFragmentManager,
                ForgotPasswordDialogFragment.TAG
            )
        }

        btnNewAccount?.setOnClickListener {
            sendToLogin02()
        }
    }

    private fun loadUser() {
        viewModelLogin.login(textEmail?.text.toString(), textPassword?.text.toString())
            .observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Loading -> {
                        progressBar?.isVisible = it.isLoading
                        btnLogin?.isVisible = it.isLoading.not()
                    }

                    is DataResult.Error -> {
                        dialogError.show(parentFragmentManager, PasswordIncorrectDialogFragment.TAG)
                    }
                    is DataResult.Empty -> {
                        Toast.makeText(context, "Erro ao Receber os Dados!", Toast.LENGTH_LONG).show()
                    }

                    is DataResult.Success -> {
                        dialogCorrect.show(parentFragmentManager, PasswordCorrectDialogFragment.TAG)

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

    private fun onGoogleSigInResult(result: GoogleLogInActivityContract.Result?) {
        if (result is GoogleLogInActivityContract.Result.Success){
            val token = result.googleSignInAccount.idToken
            Toast.makeText(context, "Meu token do Google é: $token !", Toast.LENGTH_LONG).show()
        }
    }
}
