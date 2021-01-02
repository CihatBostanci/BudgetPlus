package com.example.budgetplus.utils

import com.example.budgetplus.model.HTTPErrorModel
import com.example.budgetplus.model.response.LoginSuccessResponseModel
import retrofit2.HttpException
import java.util.regex.Matcher
import java.util.regex.Pattern

//URL
const val  baseUrl = "https://bearsbudgetmanwebapi.herokuapp.com/"
const val connectionHubUrl = "https://bearsbudgetmanwebapi.herokuapp.com/connectHub"

// Intent Constant
const val  USERINFORESPONSEMODEL = "UserInfo"
const val GROUPDETAILSRESPONSEMODEL = "GroupDetails"
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
    val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    pattern = Pattern.compile(passwordPattern)
    matcher = pattern.matcher(password)
    return matcher.matches()
}

//*******************SERVICE API URL*****************************
//Shared Pref Constant
const val TOKEN = "token"


//Account Service Constant
const val REGISTER_SERVICE = "/Account/Register"
const val LOGIN_SERVICE=  "/Account/Login"
const val AUTHENTICATE_SERVICE = "/Account/Authenticate"
const val CONFIRM_EMAIL_SERVICE = "/Account/ConfirmEmail"
const val UPDATE_VERIFICATION_SERVICE = "/Account/UpdateVerificationCode"
const val RESET_PASSWORD_SERVICE = "/Account/ResetPasswordRequest"
const val CHANGE_PASSWORD_SERVICE = "/Account/ChangePassword"
const val CONTROL_RESET_CODE_SERVICE = "/Account/ControlResetCode"
const val GET_USER_INFO_SERVICE = "/Account/GetUserInfos"

//Group Service Constant
const val GET_GROUP_DETAILS_SERVICE = "/Group/GetGroupDetails"
const val ADD_GROUP_SERVICE = "/Group/Add"

//Transaction Service Constant
const val  ADD_EXPENSE = "/Transaction/AddExpense"
const val  ADD_TRANSFER = "/Transaction/AddTransfer"

//*****************SERVICE MESSAGE****************************
const val SUCCESS_MESSAGE = "Success"
const val ERROR_MESSAGE = "Error"
const val WRONG_MESSAGE = "Wrong code"
const val CHANGE_PASSWORD_MESSAGE = "Please change your password"
const val CHECK_EMAIL_PASSWORD_MESSAGE = "Please check your email to verify your password"


//*****************ERROR MESSAGE*********************

const val FORGET_PASSWORD_ERROR_MESSAGE =  "Confirmation Password is not equal with password."
const val PASSWORD_INVALID_MESSAGE = "Password is not valid."
const val EMAIL_INVALID_MESSAGE = "Email is not valid."
const val FIRST_NAME_MESSAGE = "First name cannot empty"
const val LAST_NAME_MESSAGE = "Last name cannot empty"

//****** Transfer Bundle Key ****************
const val FROM = "FROM"
const val ADD_EXPENSE_ACTION = "ADDEXPENSE"
const val CREATE_A_GROUP_ACTION = "CREATEAGROUP"
const val TRANSFER_GROUPS_FRIEND_LIST = "FRIENDLIST"
const val GROUP_DETAIL_ACTION_INFO = "GROUPDETAILINFO"


//*****************CREATE A GROUP ERROR MESSAGE*********************
const val GROUP_DESCRIPTION_ERROR_MESSAGE= "Group Description cannot empty"
const val GROUP_NAME_ERROR_MESSAGE= "Group Name cannot empty"


//****************Add Expense*********************
const val SPINNER_TITLE_ITEM = "Please select friends"

//*****************ADD Expense ERROR MESSAGE*********************
const val ADD_EXPENSE_DESCRIPTION_ERROR_MESSAGE = "Description cannot be empty"
const val ADD_EXPENSE_ERROR_MESSAGE = "Expense cannot be empty"
const val ADD_EXPENSE_CATEGORY_ERROR_MESSAGE = "Category cannot be empty"

