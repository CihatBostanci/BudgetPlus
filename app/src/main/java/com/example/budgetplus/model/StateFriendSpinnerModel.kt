package com.example.budgetplus.model

data class StateFriendSpinnerModel(
    var title: String? = null,
    var isSelected: Boolean = false
) {

    constructor():this("", false)

    override fun toString(): String {
        return "StateFriendSpinnerModel(title=$title, isSelected=$isSelected)"
    }
}