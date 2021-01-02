package com.example.budgetplus.model.request


import com.google.gson.annotations.SerializedName

data class AddTransferTransactionRequestBodyModel(
    @SerializedName("description")
    var description: String,
    @SerializedName("groupId")
    var groupId: Int,
    @SerializedName("transactionRelatedUsers")
    var transactionRelatedUsers: MutableList<TransactionRelatedUser>,
    @SerializedName("whoAdded")
    var whoAdded: Int
)