package com.android.dicodingstoryapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.android.dicodingstoryapp.DataDummy
import com.android.dicodingstoryapp.adapter.paging.StoryAdapter
import com.android.dicodingstoryapp.data.model.StoryResponse
import com.android.dicodingstoryapp.data.model.UserModel
import com.android.dicodingstoryapp.data.repository.StoryRepository
import com.android.dicodingstoryapp.ui.addstory.AddStoryViewModel
import com.android.dicodingstoryapp.utils.MainDispatcherRule
import com.android.dicodingstoryapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var repository: StoryRepository

    @Test
    fun `Paging story data should return the correct data`() = runTest {
        val dummyStory = DataDummy.generateDummyStoryList()
        val data: PagingData<StoryResponse.StoryApp> = StorySource.snapshot(dummyStory)
        val exceptedData = MutableLiveData<PagingData<StoryResponse.StoryApp>>()
        exceptedData.value = data
        `when`(repository.getStory()).thenReturn(exceptedData)

        val storyViewModel = StoryViewModel(repository)
        val actualStory: PagingData<StoryResponse.StoryApp> = storyViewModel.getStory().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
                diffCallback = StoryAdapter.DIFF_CALLBACK,
        updateCallback = noopListUpdateCallback,
        workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory, differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
        assertEquals(dummyStory[0].id, differ.snapshot()[0]?.id)
    }

    @Test
    fun testGetUser() {
        // when getUserData is called
        val repository = Mockito.mock(StoryRepository::class.java)
        val liveData = MutableLiveData<UserModel>()
        liveData.value = UserModel("Test", "Ok", true)
        Mockito.`when`(repository.getUserData()).thenReturn(liveData)

        // Create a MapsViewModel with the mock repository
        val viewModel = StoryViewModel(repository)

        // Call getUser and verify that the correct LiveData is returned
        Assert.assertEquals(viewModel.getUser(), liveData)
    }

}

class StorySource : PagingSource<Int, LiveData<List<StoryResponse.StoryApp>>>() {
    companion object {
        fun snapshot(items: List<StoryResponse.StoryApp>): PagingData<StoryResponse.StoryApp> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoryResponse.StoryApp>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<StoryResponse.StoryApp>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}