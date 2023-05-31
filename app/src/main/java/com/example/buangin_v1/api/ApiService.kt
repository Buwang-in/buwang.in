package com.example.buangin_v1.api

import com.example.buangin_v1.response.LoginRequest
import com.example.buangin_v1.response.LoginResponse
import com.example.buangin_v1.response.RegisterRequest
import com.example.buangin_v1.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/v1/register")
    fun userRegister(
        @Body signUpRequest: RegisterRequest
    ): Call<RegisterResponse>

    @POST("/v1/login")
    fun userLogin(
        @Body loginRequest: LoginRequest
    ):Call<LoginResponse>


}
