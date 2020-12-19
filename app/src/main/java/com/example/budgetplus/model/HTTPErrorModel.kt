package com.example.budgetplus.model

import com.google.gson.annotations.SerializedName

data class HTTPErrorModel(
    @SerializedName("code")
    var code: String? = "",
    @SerializedName("message")
    var message:String? = "Error Occurred"
){
    constructor():this("","")
}