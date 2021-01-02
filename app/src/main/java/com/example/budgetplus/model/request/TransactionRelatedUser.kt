package com.example.budgetplus.model.request


import com.google.gson.annotations.SerializedName

data class TransactionRelatedUser(
    @SerializedName("amount")
    var amount: Double,
    @SerializedName("relatedUserId")
    var relatedUserId: Int
){
    constructor():this(0.0,0)
}