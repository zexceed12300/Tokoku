package com.zexceed.tokoku.models.remote.request.users

import com.google.gson.annotations.SerializedName

data class UserAddressRequest (
    @field:SerializedName("address")
    val address: String
)
