package com.example.budgetplus.model.request


import com.google.gson.annotations.SerializedName

data class GroupAddRequestBodyModel(
    @SerializedName("description")
    var description: String,
    @SerializedName("groupName")
    var groupName: String,
    @SerializedName("moneyShortCut")
    var moneyShortCut: String,
    @SerializedName("userId")
    var userId: Int,
    @SerializedName("budget")
    var budget: Double
)