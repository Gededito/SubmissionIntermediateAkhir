package com.android.dicodingstoryapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.android.dicodingstoryapp.adapter.paging.StoryPagingSource
import com.android.dicodingstoryapp.data.api.ApiService
import com.android.dicodingstoryapp.data.model.StoryResponse
import com.android.dicodingstoryapp.data.model.UserModel
import com.android.dicodingstoryapp.data.preferences.UserPreferences
import com.android.dicodingstoryapp.data.response.addstory.AddStoryResponse
import com.android.dicodingstoryapp.data.response.login.LoginRequest
import com.android.dicodingstoryapp.data.response.login.LoginResponse
import com.android.dicodingstoryapp.data.response.register.RegisterRequest
import com.android.dicodingstoryapp.data.response.register.RegisterResponse
import com.android.dicodingstoryapp.utility.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(private val pref: UserPreferences, private val apiService: ApiService) {

    fun getStory(): LiveData<PagingData<StoryResponse.StoryApp>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                StoryPagingSource(apiService, pref)
            }
        ).liveData
    }

    fun loginUser(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.userLogin(LoginRequest(email, password))
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Login", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun registerUser(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.userRegister(RegisterRequest(name, email, password))
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Register", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun storyAdd(token: String, file: MultipartBody.Part, description: RequestBody, lat: Double?, lon: Double?):
            LiveData<Result<AddStoryResponse>> = liveData {

        emit(Result.Loading)
        try {
            val response = apiService.addStory(token, file, description, lat, lon)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Register", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }

    }

    fun getLocationStory(token: String): LiveData<Result<StoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStoryLocation(token, 1)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Register", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserData(): LiveData<UserModel> {
        return pref.getUserData().asLiveData()
    }

    suspend fun saveUserData(user: UserModel) {
        pref.saveUserData(user)
    }

    suspend fun login() {
        pref.login()
    }

    suspend fun logout() {
        pref.logout()
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            preferences: UserPreferences,
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(preferences, apiService)
            }.also { instance = it }
    }

}