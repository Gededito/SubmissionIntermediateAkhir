package com.android.dicodingstoryapp.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.dicodingstoryapp.DataDummy
import com.android.dicodingstoryapp.data.model.StoryResponse
import com.android.dicodingstoryapp.data.model.StoryResponse.StoryApp
import com.android.dicodingstoryapp.data.repository.StoryRepository
import com.android.dicodingstoryapp.utility.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val responseMap = DataDummy.generateDummyNewsEntity()
    private val mapDummy = DataDummy.generateDummyNewsEntity()
    private val token = ""


    @Before
    fun setup() {
        mapsViewModel = MapsViewModel(repository)
    }

    @Test
    fun `data should return the api with a location`() {
        val observer = Observer<Result<List<StoryApp>>> {}
        try {
            val expectedNews = MutableLiveData<Result<List<StoryResponse>>>()
            expectedNews.value = Result.Success(responseMap)
            `when`(repository.getLocationStory(token)).thenReturn(expectedNews)

            val actualNews = mapsViewModel.getLocationStory(token).observeForever(observer)

            Mockito.verify(repository).getLocationStory(token)
            Assert.assertNotNull(actualNews)
        } finally {
            mapsViewModel.getLocationStory(token).removeObserver(observer)
        }
    }

}