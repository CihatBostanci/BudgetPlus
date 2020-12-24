package com.example.budgetplus.model.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserInfo(
    @SerializedName("balance")
    var balance: Int,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("lastName")
    var lastName: String
)  {
    override fun toString(): String {
        return "UserInfo(balance=$balance, firstName='$firstName', id=$id, lastName='$lastName')"
    }
}