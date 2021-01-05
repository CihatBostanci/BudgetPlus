package com.example.budgetplus.model.request

import com.google.gson.annotations.SerializedName

data class RegisterRequestBodyModel (
    @SerializedName("firstName")
    var firstName: String? = "",
    @SerializedName("lastName")
    var lastName: String? = "",
    @SerializedName("email")
    var email: String? = "",
    @SerializedName("password")
    var password: String? = "",
    @SerializedName("confirmPassword")
    var confirmPassword: String? = "",

){
    constructor() : this("","","","","")
}