package com.android.dicodingstoryapp.data.response.login

import com.google.gson.annotations.SerializedName

data class LoginResult(
    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)
