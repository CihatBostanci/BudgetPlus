package com.example.budgetplus.model.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserInfoResponseModel(
    @SerializedName("email")
    var email: String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("shortName")
    var shortName: String,
    @SerializedName("userId")
    var userId: Int
):Serializable {
    constructor():this("","","","",0)
    override fun toString(): String {
        return "UserInfoResponseModel(email='$email', firstName='$firstName', lastName='$lastName', shortName='$shortName', userId=$userId)"
    }
}