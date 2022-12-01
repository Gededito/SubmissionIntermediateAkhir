package com.android.dicodingstoryapp

import com.android.dicodingstoryapp.data.model.StoryResponse
import com.android.dicodingstoryapp.data.response.addstory.AddStoryResponse

object DataDummy {
    fun addStoryResponse(): AddStoryResponse {
        return AddStoryResponse(false, "Ok")
    }

    fun generateDummyNewsEntity(): List<StoryResponse.StoryApp> {
        val newsList = ArrayList<StoryResponse.StoryApp>()
        for (i in 0..10) {
            val news = StoryResponse.StoryApp(
                "ID",
                "gede",
                "desc",
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "Thursday, October 20, 2022 at 3:03:44 PM Indochina Time",
                -6.1947508,
                106.4867868
            )
            newsList.add(news)
        }
        return newsList
    }

}