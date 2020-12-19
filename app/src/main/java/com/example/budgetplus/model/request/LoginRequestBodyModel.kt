package com.example.budgetplus.model.request

import com.google.gson.annotations.SerializedName

/**
 * {
"email": "user@example.com",
"password": "string"
}
 *
 */
data class LoginRequestBodyModel(
    @SerializedName("email")
    var email:String? = "",
    @SerializedName("password")
    var password:String? = ""
){

}