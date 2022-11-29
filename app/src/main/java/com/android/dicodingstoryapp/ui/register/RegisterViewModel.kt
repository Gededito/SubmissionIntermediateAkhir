package com.android.dicodingstoryapp.ui.register

import androidx.lifecycle.ViewModel
import com.android.dicodingstoryapp.data.repository.StoryRepository

class RegisterViewModel (private val storyRepository: StoryRepository): ViewModel() {

    fun userRegister(name: String, email: String, password: String) =
        storyRepository.registerUser(name, email, password)

}