package com.android.dicodingstoryapp

import com.android.dicodingstoryapp.data.response.addstory.AddStoryResponse

object DataDummy {
    fun addStoryResponse(): AddStoryResponse {
        return AddStoryResponse(false, "Ok")
    }
}