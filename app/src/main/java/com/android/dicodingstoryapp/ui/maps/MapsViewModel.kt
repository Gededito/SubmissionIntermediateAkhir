package com.android.dicodingstoryapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.dicodingstoryapp.data.model.UserModel
import com.android.dicodingstoryapp.data.repository.StoryRepository

class MapsViewModel (private val repository: StoryRepository): ViewModel() {

    fun getLocationStory(token: String) = repository.getLocationStory(token)

    fun getUser(): LiveData<UserModel> {
        return repository.getUserData()
    }

}