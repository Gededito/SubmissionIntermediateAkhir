package com.android.dicodingstoryapp.ui.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.dicodingstoryapp.data.model.UserModel
import com.android.dicodingstoryapp.data.repository.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddStoryViewModel(private val storyRepository: StoryRepository): ViewModel() {

    fun storyAdd(token: String, file: MultipartBody.Part, description: RequestBody,
                lat: Double?, lon: Double?) =
        storyRepository.storyAdd(token, file, description, lat, lon)

    fun getUser(): LiveData<UserModel> {
        return storyRepository.getUserData()
    }

}