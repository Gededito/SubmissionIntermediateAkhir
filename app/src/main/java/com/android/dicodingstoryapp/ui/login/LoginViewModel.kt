package com.android.dicodingstoryapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.dicodingstoryapp.data.model.UserModel
import com.android.dicodingstoryapp.data.repository.StoryRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val storyRepository: StoryRepository): ViewModel() {

    fun loginUser(email: String, password: String) = storyRepository.loginUser(email, password)

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            storyRepository.saveUserData(user)
        }
    }

    fun login() {
        viewModelScope.launch {
            storyRepository.login()
        }
    }

    fun logout() {
        viewModelScope.launch {
            storyRepository.logout()
        }
    }

}