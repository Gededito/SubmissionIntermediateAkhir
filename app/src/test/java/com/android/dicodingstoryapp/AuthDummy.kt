package com.android.dicodingstoryapp

import com.android.dicodingstoryapp.data.response.login.LoginResponse
import com.android.dicodingstoryapp.data.response.login.LoginResult
import com.android.dicodingstoryapp.data.response.register.RegisterResponse

object AuthDummy {
    fun provideLoginResponse(): LoginResponse = LoginResponse(LoginResult("", "IDK412", "ABCD"), false, "ok")

    fun provideRegisterResponse(): RegisterResponse = RegisterResponse(false, "Ok")
}