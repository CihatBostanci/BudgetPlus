package com.example.budgetplus.model.request

data class ConfirmRequestBodyModel(
    var code: String,
    var email: String
){
    constructor():this("","")
}