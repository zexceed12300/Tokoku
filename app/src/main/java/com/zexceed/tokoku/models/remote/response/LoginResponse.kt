package com.zexceed.tokoku.models.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("firstName")
    val firstName: String,

    @field:SerializedName("lastName")
    val lastName: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("token")
    val token: String,
)
