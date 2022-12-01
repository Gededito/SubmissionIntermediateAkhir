package com.android.dicodingstoryapp

import com.android.dicodingstoryapp.data.model.StoryResponse
import com.android.dicodingstoryapp.data.response.addstory.AddStoryResponse

object DataDummy {
    fun addStoryResponse(): AddStoryResponse {
        return AddStoryResponse(false, "Ok")
    }

    fun storyResponseLocation(): StoryResponse {
        return StoryResponse(false, "Ok", generateDummyStoryLocation())
    }

    fun storyListResponse(): StoryResponse {
        return StoryResponse(false, "Ok", generateDummyStoryList())
    }

    private fun generateDummyStoryLocation(): List<StoryResponse.StoryApp> {
        val storyList = ArrayList<StoryResponse.StoryApp>()
        for (i in 0..10) {
            val story = StoryResponse.StoryApp(
                "ID",
                "gede",
                "desc",
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "Thursday, October 20, 2022 at 3:03:44 PM Indochina Time",
                -6.1947508,
                106.4867868
            )
            storyList.add(story)
        }
        return storyList
    }

    fun generateDummyStoryList(): List<StoryResponse.StoryApp>{
        val storyList = ArrayList<StoryResponse.StoryApp>()
        for (i in 0..100) {
            val story = StoryResponse.StoryApp(
                i.toString(),
                "name + $i",
                "desc + $i",
                "$i",
                "created + $i",
                i.toDouble(),
                i.toDouble()
            )
            storyList.add(story)
        }
        return storyList
    }

}