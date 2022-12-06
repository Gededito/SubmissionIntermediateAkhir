package com.android.dicodingstoryapp.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.android.dicodingstoryapp.AuthDummy
import com.android.dicodingstoryapp.data.repository.StoryRepository
import com.android.dicodingstoryapp.data.response.register.RegisterResponse
import com.android.dicodingstoryapp.utility.Result
import com.android.dicodingstoryapp.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: StoryRepository
    private lateinit var registerViewModel: RegisterViewModel
    private val auth = AuthDummy.provideRegisterResponse()
    private val name = "gede"
    private val email = "dito999dito@gmail.com"
    private val password = "6723823"

    @Before
    fun setup() {
        registerViewModel = RegisterViewModel(repository)
    }

    @Test
    fun `when register called from repository it should return Success and not null`() {
        val expected = MutableLiveData<Result<RegisterResponse>>()
        expected.value = Result.Success(auth)
        `when`(repository.registerUser(name, email, password)).thenReturn(expected)

        val actual = registerViewModel.userRegister(name, email, password).getOrAwaitValue()
        Assert.assertTrue(actual is Result.Success)
        Assert.assertNotNull(actual)
    }

    @Test
    fun `when register failed it should return Error and also not null`(){
        val expected = MutableLiveData<Result<RegisterResponse>>()
        expected.value = Result.Error("Something Error")
        `when`(repository.registerUser(name, email, password)).thenReturn(expected)

        val actual = registerViewModel.userRegister(name, email, password).getOrAwaitValue()
        Assert.assertTrue(actual is Result.Error)
        Assert.assertNotNull(actual)
    }

}