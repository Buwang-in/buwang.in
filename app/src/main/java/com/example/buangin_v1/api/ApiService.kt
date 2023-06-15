package com.example.buangin_v1.api

import com.example.buangin_v1.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/api")
    fun userRegister(
        @Body signUpRequest: RegisterRequest
    ): Call<RegisterResponse>

    @POST("/api/login")
    fun userLogin(
        @Body loginRequest: LoginRequest
    ):Call<LoginResponse>


    @Multipart
    @POST("/object-to-json")
    fun detectImage(@Part image: MultipartBody.Part): Call<DetecResponse>

    @Multipart
    @POST("/object-to-img")
    fun detectTrash(@Part image: MultipartBody.Part): Call<DetecResponse>

}
