package com.example.budgetplus.utils

import com.example.budgetplus.model.HTTPErrorModel
import com.example.budgetplus.model.response.LoginSuccessResponseModel
import retrofit2.HttpException
import java.util.regex.Matcher
import java.util.regex.Pattern

const val  baseUrl = "https://bearsbudgetmanwebapi.herokuapp.com/"
const val connectionHubUrl = "https://bearsbudgetmanwebapi.herokuapp.com/connectHub"

//*****************************************************************
//Api Call Status Type
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
//*****************************************************************
// Error Constants
fun errorHTTP400Handler(exception: HttpException): HTTPErrorModel?{
    var errorResponseBody = HTTPErrorModel()

    exception.response()?.errorBody()?.let {
         errorResponseBody= IntentUtil.gson.fromJson(
           it.string() , HTTPErrorModel::class.java
        )
    }

    return errorResponseBody
}
const val SENDVERIFYINFO = "Please check your email."
const val DEFAULTERRORMESSAGE = "ERROR OCCURRED"

//*****************************************************************
//Validation
val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

fun isValidPassword(password: String?): Boolean {
    val pattern: Pattern
    val matcher: Matcher
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    pattern = Pattern.compile(PASSWORD_PATTERN)
    matcher = pattern.matcher(password)
    return matcher.matches()
}

//*******************SERVICE API URL*****************************
//Shared Pref Constant
const val TOKEN = "token"


//Account Service Constant
const val REGISTERSERVICE = "/Account/Register"
const val LOGINSERVICE=  "/Account/Login"
const val AUTHENTICATESERVICE = "/Account/Authenticate"
const val CONFIRMEMAILSERVICE = "/Account/ConfirmEmail"
const val UPDATEVERIFICATIONSERVICE = "/Account/UpdateVerificationCode"
const val RESETPASSWORDSERVICE = "/Account/ResetPasswordRequest"
const val CHANGEPASSWORDSERVICE = "/Account/ChangePassword"
const val CONTROLRESETCODESERVICE = "/Account/ControlResetCode"
const val GETUSERINFOSERVICE = "/Account/GetUserInfos"

//Group Service Constant
const val GETGROUPDETAILSSERVICE = "/Group/GetGroupDetails"

//*****************SERVICE MESSAGE****************************
const val SUCCESSMESSAGE = "Success"
const val ERRORMESSAGE = "Error"
const val WRONGMESSAGE= "Wrong code"
const val CHANGEPASSWORDMESSAGE = "Please change your password"
const val CHECKEMAILPASSWORDMESSAGE = "Please check your email to verify your password"


//*****************ERRO MESSAGE*********************

const val FORGETPASSWORDERRORMESSAGE =  "Confirmation Password is not equal with password."
const val PASSWORDINVALIDMESSAGE = "Password is not valid."
const val EMAILINVALIDMESSAGE = "Email is not valid."
const val FIRSTNAMEMESSAGE = "First name cannot empty"
const val LASTNAMEMESSAGE = "Last name cannot empty"

