package com.example.budgetplus.model.response

import com.google.gson.annotations.SerializedName

/**
 * {
"isLoggedIn": true,
"isEmailConfirmed": true,
"firstName": "string",
"lastName": "string",
"email": "string",
"shortName": "string",
"userId": 0,
"token": "string"
}
 *
 */
data class LoginSuccessResponseModel(
    @SerializedName("isLoggedIn")
    var isLoggedIn:Boolean? = false,
    @SerializedName("isEmailConfirmed")
    var isEmailConfirmed:Boolean? = false,
    @SerializedName("firstName")
    var firstName:String? = "",
    @SerializedName("lastName")
    var lastName:String? = "",
    @SerializedName("email")
    var email:String? = "",
    @SerializedName("shortName")
    var shortName:String? = "",
    @SerializedName("userId")
    var userId:Int? = 0,
    @SerializedName("token")
    var token:String? = ""
    ) {
    override fun toString(): String {
        return super.toString()
    }
}