package com.android.dicodingstoryapp.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.android.dicodingstoryapp.DataDummy
import com.android.dicodingstoryapp.data.model.StoryResponse
import com.android.dicodingstoryapp.data.model.UserModel
import com.android.dicodingstoryapp.data.repository.StoryRepository
import com.android.dicodingstoryapp.utility.Result
import com.android.dicodingstoryapp.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val mapResponse = DataDummy.storyResponseLocation()
    private val token = ""


    @Before
    fun setup() {
        mapsViewModel = MapsViewModel(repository)
    }

    @Test
    fun `data should return the api with a location`() {
        val exceptedLiveData = MutableLiveData<Result<StoryResponse>>()
        exceptedLiveData.value = Result.Success(mapResponse)

        val expected = exceptedLiveData.getOrAwaitValue()
        `when`(repository.getLocationStory(token)).thenReturn(exceptedLiveData)
        val actual = mapsViewModel.getLocationStory(token).getOrAwaitValue()

        Assert.assertNotNull(actual)
        assertTrue(actual is Result.Success)
        assertEquals(expected, actual)
    }

    @Test
    fun testGetUser() {
        // when getUserData is called
        val repository = mock(StoryRepository::class.java)
        val liveData = MutableLiveData<UserModel>()
        liveData.value = UserModel("Test", "Ok", true)
        `when`(repository.getUserData()).thenReturn(liveData)

        // Create a MapsViewModel with the mock repository
        val viewModel = MapsViewModel(repository)

        // Call getUser and verify that the correct LiveData is returned
        assertEquals(viewModel.getUser(), liveData)
    }

}