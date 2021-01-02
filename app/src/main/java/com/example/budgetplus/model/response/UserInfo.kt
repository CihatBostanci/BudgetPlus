package com.example.budgetplus.model.response


import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("balance")
    var balance: Double,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("lastName")
    var lastName: String
)