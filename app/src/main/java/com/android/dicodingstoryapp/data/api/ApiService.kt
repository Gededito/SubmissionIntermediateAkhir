package com.android.dicodingstoryapp.data.api

import com.android.dicodingstoryapp.data.model.StoryResponse
import com.android.dicodingstoryapp.data.response.addstory.AddStoryResponse
import com.android.dicodingstoryapp.data.response.login.LoginRequest
import com.android.dicodingstoryapp.data.response.login.LoginResponse
import com.android.dicodingstoryapp.data.response.register.RegisterRequest
import com.android.dicodingstoryapp.data.response.register.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun userRegister(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun userLogin(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("stories")
    suspend fun getAllStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse

    @GET("stories")
    suspend fun getStoryLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 1,
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?
    ): AddStoryResponse

}