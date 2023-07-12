package com.zexceed.tokoku.models.remote.response.users

import com.google.gson.annotations.SerializedName

data class UserAddressReponse (
    @field:SerializedName("address")
    val address: String
)
