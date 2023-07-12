package com.zexceed.tokoku.models.remote.request.users

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @field:SerializedName("firstName")
    val firstName: String,

    @field:SerializedName("lastName")
    val lastName: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("address")
    val address: UserAddressRequest
)
