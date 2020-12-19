package com.example.budgetplus.model.response

import com.google.gson.annotations.SerializedName

/**
 *
 *"email": "string",
"isRegistered": true
 */
data class DataRegisterResponseModel(
    @SerializedName("email")
    var email: String = "",
   @SerializedName("isRegistered")
   var isRegistered: Boolean = false
) {
    override fun toString(): String {
        return "DataRegister(email='$email', isRegistered=$isRegistered)"
    }
}