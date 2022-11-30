package com.android.dicodingstoryapp.ui.addstory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.android.dicodingstoryapp.DataDummy
import com.android.dicodingstoryapp.data.repository.StoryRepository
import com.android.dicodingstoryapp.data.response.addstory.AddStoryResponse
import com.android.dicodingstoryapp.utility.Result
import com.android.dicodingstoryapp.utils.getOrAwaitValue
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel
    private val auth = DataDummy.addStoryResponse()
    private val token = ""
    private val file = Mockito.mock(File::class.java)
    private val lat = -6.1947508
    private val lon = 106.4867868

    @Before
    fun setup() {
        addStoryViewModel = AddStoryViewModel(repository)
    }

    @Test
    fun `posting live data should return success when invoking upload to Server`(){
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val photo: MultipartBody.Part =  MultipartBody.Part.createFormData("photo", file.name, requestImageFile)
        val description = "description".toRequestBody("text/plain".toMediaType())

        val expectedLiveData = MutableLiveData<Result<AddStoryResponse>>()
        expectedLiveData.value = Result.Success(auth)

        val expected = expectedLiveData.getOrAwaitValue()

        Mockito.`when`(repository.storyAdd(token, photo, description, lat, lon)).thenReturn(expectedLiveData)
        val actual = addStoryViewModel.storyAdd(token, photo, description,lat, lon).getOrAwaitValue()

        Mockito.verify(repository).storyAdd(token, photo, description, lat, lon)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(expected, actual)
    }

}