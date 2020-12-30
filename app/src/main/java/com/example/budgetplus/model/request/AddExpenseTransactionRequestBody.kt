package com.example.budgetplus.model.request


import com.google.gson.annotations.SerializedName

data class AddExpenseTransactionRequestBody(
    @SerializedName("amount")
    var amount: Double,
    @SerializedName("category")
    var category: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("groupId")
    var groupId: Int,
    @SerializedName("relatedUserIds")
    var relatedUserIds: MutableList<Int>,
    @SerializedName("transactionType")
    var transactionType: String,
    @SerializedName("whoAdded")
    var whoAdded: Int
)