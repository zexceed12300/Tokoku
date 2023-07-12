package com.zexceed.tokoku.models.remote.response.users

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("firstName")
    val firstName: String,

    @field:SerializedName("lastName")
    val lastName: String,

    @field:SerializedName("maidenName")
    val maidenName: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("address")
    val address: UserAddressReponse
)
