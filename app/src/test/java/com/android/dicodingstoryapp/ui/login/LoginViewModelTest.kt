package com.android.dicodingstoryapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.android.dicodingstoryapp.AuthDummy
import com.android.dicodingstoryapp.data.repository.StoryRepository
import com.android.dicodingstoryapp.data.response.login.LoginResponse
import com.android.dicodingstoryapp.utility.Result
import com.android.dicodingstoryapp.utils.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository
    private lateinit var loginViewModel: LoginViewModel
    private val auth = AuthDummy.provideLoginResponse()
    private val email = "dito999dito@gmail.com"
    private val password = "6723823"

    @Before
    fun setup() {
        loginViewModel = LoginViewModel(repository)
    }

    @Test
    fun `if login success then return Success`() {
        val expectedLiveData = MutableLiveData<Result<LoginResponse>>()
        expectedLiveData.value = Result.Success(auth)

        val expected = expectedLiveData.getOrAwaitValue()

        `when`(repository.loginUser(email, password)).thenReturn(expectedLiveData)
        val actual = loginViewModel.loginUser(email, password).getOrAwaitValue()

        Mockito.verify(repository).loginUser(email, password)
        assertNotNull(actual)
        assertTrue(actual is Result.Success)
        assertEquals(expected, actual)
    }

    @Test
    fun `if network error then return Async Error`() {
        val expectedLiveData = MutableLiveData<Result<LoginResponse>>(Result.Error("Dummy"))
        `when`(repository.loginUser(email, password)).thenReturn(expectedLiveData)

        val actual = loginViewModel.loginUser(email,password).getOrAwaitValue()
        Mockito.verify(repository).loginUser(email, password)
        assertTrue(actual is Result.Error)
        assertNotNull(actual)
    }

}