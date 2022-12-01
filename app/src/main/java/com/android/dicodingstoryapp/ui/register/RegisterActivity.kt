package com.android.dicodingstoryapp.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.dicodingstoryapp.databinding.ActivityRegisterBinding
import com.android.dicodingstoryapp.ui.login.LoginActivity
import com.android.dicodingstoryapp.utility.Result
import com.android.dicodingstoryapp.utility.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupViewModel()
        playAnimation()
        actionButton()
    }

    private fun actionButton() {
        binding.btnRegister.setOnClickListener {
            register()
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun register() {
        val name = binding.edtNameUser.text.toString()
        val email = binding.edtEmailUser.text.toString()
        val password = binding.edtPasswordUser.text.toString()
        registerViewModel.userRegister(name, email, password).observe(this) {
            when (it) {
                is Result.Success -> {
                    showLoading(false)
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    Toast.makeText(this, "Akun Sudah Terdaftar", Toast.LENGTH_LONG).show()
                    showLoading(false)
                }
            }
        }
    }

    private fun setupViewModel() {
        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        registerViewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]
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
        val titleRegister = ObjectAnimator.ofFloat(binding.tvRegisterTitle, View.ALPHA, 1f).setDuration(500)
        val subTitleRegister = ObjectAnimator.ofFloat(binding.tvRegisterSubTitle, View.ALPHA, 1f).setDuration(500)

        // Name
        val textName = ObjectAnimator.ofFloat(binding.tvNameUser, View.ALPHA, 1f).setDuration(500)
        val inputName = ObjectAnimator.ofFloat(binding.tlNameUser, View.ALPHA, 1f).setDuration(500)

        // Email
        val textEmail = ObjectAnimator.ofFloat(binding.tvEmailUser, View.ALPHA, 1f).setDuration(500)
        val inputEmail = ObjectAnimator.ofFloat(binding.tlEmailUser, View.ALPHA, 1f).setDuration(500)

        // Password
        val textPassword = ObjectAnimator.ofFloat(binding.tvPasswordUser, View.ALPHA, 1f).setDuration(500)
        val inputPassword = ObjectAnimator.ofFloat(binding.tlPasswordUser, View.ALPHA, 1f).setDuration(500)

        // Button
        val buttonRegister = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val logLayout = ObjectAnimator.ofFloat(binding.layoutLogin, View.ALPHA, 1f).setDuration(500)

        val title = AnimatorSet().apply {
            playTogether(titleRegister, subTitleRegister)
        }

        val name = AnimatorSet().apply {
            playTogether(textName, inputName)
        }

        val email = AnimatorSet().apply {
            playTogether(textEmail, inputEmail)
        }

        val password = AnimatorSet().apply {
            playTogether(textPassword, inputPassword)
        }

        val button = AnimatorSet().apply {
            playTogether(buttonRegister, logLayout)
        }

        AnimatorSet().apply {
            playSequentially(
                name,
                title,
                email,
                password,
                button
            )
            start()
        }

    }

}