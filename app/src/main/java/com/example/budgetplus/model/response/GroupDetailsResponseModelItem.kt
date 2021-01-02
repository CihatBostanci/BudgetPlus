package com.example.budgetplus.model.response


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GroupDetailsResponseModelItem(
    @SerializedName("adminId")
    var adminId: Int,
    @SerializedName("budget")
    var budget: Int,
    @SerializedName("currency")
    var currency: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("expenseCategories")
    var expenseCategories: MutableList<ExpenseCategory>,
    @SerializedName("expenseGroup")
    var expenseGroup: ExpenseGroup,
    @SerializedName("groupId")
    var groupId: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("shareCode")
    var shareCode: String,
    @SerializedName("transactionInfos")
    var transactionInfos: MutableList<TransactionInfo>,
    @SerializedName("userInfos")
    var userInfos: MutableList<UserInfo>
) : Serializable