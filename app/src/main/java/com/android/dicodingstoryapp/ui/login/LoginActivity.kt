package com.android.dicodingstoryapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.dicodingstoryapp.data.model.UserModel
import com.android.dicodingstoryapp.databinding.ActivityLoginBinding
import com.android.dicodingstoryapp.ui.home.MainActivity
import com.android.dicodingstoryapp.ui.register.RegisterActivity
import com.android.dicodingstoryapp.utility.Result
import com.android.dicodingstoryapp.utility.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupViewModel()
        playAnimation()
        setAction()
    }

    private fun setAction() {
        binding.btnLogin.setOnClickListener {
            userLogin()
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun userLogin() {
        val email = binding.edtEmailUser.text.toString()
        val password = binding.edtPasswordUser.text.toString()
        loginViewModel.loginUser(email, password).observe(this) {
            when (it) {
                is Result.Success -> {
                    showLoading(false)
                    val response = it.data
                    saveDataUser(UserModel(
                        response.loginResult.name.toString(),
                        response.loginResult.token.toString(),
                        true
                    ))
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, "User Gagal Login", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun saveDataUser(user: UserModel) {
        loginViewModel.saveUser(user)
    }

    private fun setupViewModel() {
        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        loginViewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

    private fun playAnimation() {

        // Title
        val titleLogin = ObjectAnimator.ofFloat(binding.tvLoginTitle, View.ALPHA, 1f).setDuration(500)
        val subTitleLogin = ObjectAnimator.ofFloat(binding.tvLoginSubTitle, View.ALPHA, 1f).setDuration(500)

        // Email
        val textEmail = ObjectAnimator.ofFloat(binding.tvEmailUser, View.ALPHA, 1f).setDuration(500)
        val inputEmail = ObjectAnimator.ofFloat(binding.tlEmailUser, View.ALPHA, 1f).setDuration(500)

        // Password
        val textPassword = ObjectAnimator.ofFloat(binding.tvPasswordUser, View.ALPHA, 1f).setDuration(500)
        val inputPassword = ObjectAnimator.ofFloat(binding.tlPasswordUser, View.ALPHA, 1f).setDuration(500)

        // Button
        val buttonLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val regisLayout = ObjectAnimator.ofFloat(binding.layoutRegister, View.ALPHA, 1f).setDuration(500)

        val title = AnimatorSet().apply {
            playTogether(titleLogin, subTitleLogin)
        }

        val email = AnimatorSet().apply {
            playTogether(textEmail, inputEmail)
        }

        val password = AnimatorSet().apply {
            playTogether(textPassword, inputPassword)
        }

        val button = AnimatorSet().apply {
            playTogether(buttonLogin, regisLayout)
        }

        AnimatorSet().apply {
            playSequentially(
                title,
                email,
                password,
                button
            )
            start()
        }
    }

}
