package com.android.dicodingstoryapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.android.dicodingstoryapp.data.api.ApiConfig
import com.android.dicodingstoryapp.data.preferences.UserPreferences
import com.android.dicodingstoryapp.data.repository.StoryRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storyapp")

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(preferences, apiService)
    }
}