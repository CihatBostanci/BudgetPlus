package com.example.budgetplus.model.response


import com.google.gson.annotations.SerializedName

data class RelatedUser(
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("userId")
    var userId: Int
) {
    constructor():this("","",0)
    override fun toString(): String {
        return "RelatedUser(firstName='$firstName', lastName='$lastName', userId=$userId)"
    }
}