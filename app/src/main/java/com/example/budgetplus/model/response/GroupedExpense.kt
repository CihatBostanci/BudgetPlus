package com.example.budgetplus.model.response


import com.google.gson.annotations.SerializedName

data class GroupedExpense(
    @SerializedName("category")
    var category: String,
    @SerializedName("categoryTotal")
    var categoryTotal: Double,
    @SerializedName("percentage")
    var percentage: Double
)