package com.example.budgetplus.model.request

/**
 *
 */
data class ChangePasswordRequestBodyModel(
    var code: String,
    var confirmPassword: String,
    var email: String,
    var password: String
){
    constructor():this("","","","")
}