package com.jairbarzola.zoluxionescodechallenge.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.jairbarzola.zoluxionescodechallenge.R
import com.jairbarzola.zoluxionescodechallenge.data.repository.LoginRepositoryImpl
import com.jairbarzola.zoluxionescodechallenge.data.service.LoginDataSourceImpl
import com.jairbarzola.zoluxionescodechallenge.domain.usecase.VerifyCredentialsUseCase
import com.jairbarzola.zoluxionescodechallenge.presentation.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val viewModel: LoginViewModel by lazy {
        LoginViewModel(
            VerifyCredentialsUseCase(
                LoginRepositoryImpl(
                    LoginDataSourceImpl()
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupObservers()
        setupView()
    }

    private fun setupView() {
        loginEnterButton.setOnClickListener {
            viewModel.verifyCredentials(
                loginUserEditText.text.toString(),
                loginPasswordEditText.text.toString()
            )
        }
    }

    private fun setupObservers() {
        viewModel.viewStateMutable.observe(this, Observer {
            when (it) {
                is LoginResult.ShowLoading -> {
                    setForm(false)
                }
                is LoginResult.GoToHome -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                is LoginResult.ShowErrorGeneral -> {
                    setForm(true)
                    loginUserLayout.error = null
                    loginPasswordLayout.error = null
                    loginUserEditText.setText("")
                    loginPasswordEditText.setText("")
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                }
                is LoginResult.ShowUserError -> {
                    setForm(true)
                    loginUserLayout.error = it.error
                }
                is LoginResult.ShowPasswordError -> {
                    setForm(true)
                    loginPasswordEditText.error = it.error
                }
            }
        })
    }

    private fun setForm(state: Boolean) {
        loginEnterButton.run {
            if (state) {
                text = getString(R.string.enter)
                background = ContextCompat.getDrawable(this@LoginActivity,R.color.purple_700)
            } else {
                text = getString(R.string.entering)
                background = ContextCompat.getDrawable(this@LoginActivity,R.color.disabled)
            }
        }
        loginEnterButton.isEnabled = state
        loginPasswordLayout.isEnabled = state
        loginUserLayout.isEnabled = state
    }
}