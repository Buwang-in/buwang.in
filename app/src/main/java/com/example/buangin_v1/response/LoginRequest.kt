package com.example.buangin_v1.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginRequest {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null
}
