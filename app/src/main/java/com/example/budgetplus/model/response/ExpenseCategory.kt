package com.example.budgetplus.model.response


import com.google.gson.annotations.SerializedName

data class ExpenseCategory(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("type")
    var type: Int
)