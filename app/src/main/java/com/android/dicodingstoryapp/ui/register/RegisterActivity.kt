package com.android.dicodingstoryapp.ui.register

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

}