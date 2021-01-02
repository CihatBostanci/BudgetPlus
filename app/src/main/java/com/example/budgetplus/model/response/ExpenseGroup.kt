package com.example.budgetplus.model.response


import com.google.gson.annotations.SerializedName

data class ExpenseGroup(
    @SerializedName("groupedExpenses")
    var groupedExpenses: MutableList<GroupedExpense>,
    @SerializedName("total")
    var total: Int
)