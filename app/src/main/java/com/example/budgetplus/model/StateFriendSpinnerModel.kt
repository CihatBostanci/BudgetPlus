package com.example.budgetplus.model

data class StateFriendSpinnerModel(
    var title: String? = null,
    var isSelected: Boolean = false,
    var userId:Int
) {

    constructor():this("", false,0)

    override fun toString(): String {
        return "StateFriendSpinnerModel(title=$title, isSelected=$isSelected, userId=$userId)"
    }


}