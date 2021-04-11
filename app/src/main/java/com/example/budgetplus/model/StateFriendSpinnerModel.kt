package com.example.budgetplus.model

data class StateFriendSpinnerModel(
    var title: String? = null,
    var isSelected: Boolean = true,
    var userId:Int?
) {
    constructor() : this(null, true, null)


    override fun toString(): String {
        return "StateFriendSpinnerModel(title=$title, isSelected=$isSelected, userId=$userId)"
    }


}