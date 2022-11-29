package com.android.dicodingstoryapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.dicodingstoryapp.data.model.StoryResponse
import com.android.dicodingstoryapp.data.model.UserModel
import com.android.dicodingstoryapp.data.repository.StoryRepository

class StoryViewModel (private val repository: StoryRepository): ViewModel() {

    fun getStory(): LiveData<PagingData<StoryResponse.StoryApp>> {
        return repository.getStory().cachedIn(viewModelScope)
    }

    fun getUser(): LiveData<UserModel> {
        return repository.getUserData()
    }

}