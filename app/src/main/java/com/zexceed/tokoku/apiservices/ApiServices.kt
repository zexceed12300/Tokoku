package com.zexceed.tokoku.apiservices

import com.zexceed.tokoku.models.remote.request.LoginRequest
import com.zexceed.tokoku.models.remote.request.users.UserRequest
import com.zexceed.tokoku.models.remote.response.LoginResponse
import com.zexceed.tokoku.models.remote.response.products.ProductsResponse
import com.zexceed.tokoku.models.remote.response.users.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @POST("auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @GET("products/search")
    fun getProducts(
        @Query("q") query: String
    ) : Call<ProductsResponse>

    @GET("users/{id}")
    fun getUser(
        @Path("id") id: String
    ) : Call<UserResponse>

    @POST("users/add")
    fun postUser(
        @Body data: UserRequest
    ) : Call<UserResponse>
}