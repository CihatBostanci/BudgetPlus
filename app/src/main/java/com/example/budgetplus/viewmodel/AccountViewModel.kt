package com.example.budgetplus.viewmodel

import androidx.lifecycle.liveData
import com.example.budgetplus.model.request.ChangePasswordRequestBodyModel
import com.example.budgetplus.model.request.ConfirmRequestBodyModel
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.model.request.RegisterRequestBodyModel
import com.example.budgetplus.model.response.DataRegisterResponseModel
import com.example.budgetplus.model.response.LoginSuccessResponseModel
import com.example.budgetplus.model.response.UserInfoResponseModel
import com.example.budgetplus.repository.AccountRepository
import com.example.budgetplus.utils.DEFAULTERRORMESSAGE
import com.example.budgetplus.utils.IntentUtil.gson
import com.example.budgetplus.utils.Resource
import com.example.budgetplus.utils.errorHTTP400Handler
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException

class AccountViewModel : BaseViewModel() {


    fun accountRegister(
        registerRequestBodyModel: RegisterRequestBodyModel
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {
            val dataModel = AccountRepository.registerAccount(

            registerRequestBodyModel
        )
        emit(
            Resource.success(
                gson.fromJson(
                    dataModel.charStream(), DataRegisterResponseModel::class.java
                )
            )
        )
        } catch (exception: HttpException){
                when(exception.code()){
                    400 -> {

                        emit(
                            Resource.error(
                                data = null,
                                message =  errorHTTP400Handler(exception)?.message ?: DEFAULTERRORMESSAGE
                            )
                        )
                    }
                }
            }
   }

    fun accountLogin(
        loginRequestBodyModel: LoginRequestBodyModel,
        token :String
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val dataModel = AccountRepository.loginAccount(
                loginRequestBodyModel,
                token
            )
            emit(
                Resource.success(
                    gson.fromJson(
                        dataModel.charStream(), LoginSuccessResponseModel::class.java
                    )
                )
            )
        } catch (exception: HttpException){
            when(exception.code()){
                400 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message =  errorHTTP400Handler(exception)?.message ?: DEFAULTERRORMESSAGE
                        )
                    )
                }
                401 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message = exception.message()
                        )
                    )
                }
                else -> emit(
                    Resource.error(
                        data = null,
                        message = DEFAULTERRORMESSAGE
                    )
                )
            }
        }
    }

    fun authenticateAccount(
        loginRequestBodyModel: LoginRequestBodyModel
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val dataModel = AccountRepository.authenticateAccount(
                loginRequestBodyModel
            )
            emit(
                Resource.success(
                    gson.fromJson(
                        dataModel.charStream(), LoginSuccessResponseModel::class.java
                    )
                )
            )
        } catch (exception: HttpException){
            when(exception.code()){
                400 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message =  errorHTTP400Handler(exception)?.message ?: DEFAULTERRORMESSAGE
                        )
                    )
                }
                401 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message = exception.message()
                        )
                    )
                }
                else -> emit(
                    Resource.error(
                        data = null,
                        message = DEFAULTERRORMESSAGE
                    )
                )
            }
        }
    }

    fun accountConfirmEmail(
        confirmRequestBodyModel: ConfirmRequestBodyModel,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val dataModel = AccountRepository.accountConfirmEmail(
                confirmRequestBodyModel
            )
            emit(
                Resource.success(
                    gson.fromJson(
                        dataModel.charStream(), LoginSuccessResponseModel::class.java
                    )
                )
            )
        } catch (exception: HttpException){
            when(exception.code()){
                400 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message =  errorHTTP400Handler(exception)?.message ?: DEFAULTERRORMESSAGE
                        )
                    )
                }
                401 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message = exception.message()
                        )
                    )
                }
                else -> emit(
                    Resource.error(
                        data = null,
                        message = DEFAULTERRORMESSAGE
                    )
                )
            }
        }
    }

    fun updateVerificationCode(
        loginRequestBodyModel: LoginRequestBodyModel
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val dataModel = AccountRepository.updateVerificationCode(
                loginRequestBodyModel
            )
            emit(
                //Generic Success response load data with boolean because of service
                Resource.success(
                   data = true
                )
            )
        } catch (exception: HttpException){
            when(exception.code()){
                400 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message =  errorHTTP400Handler(exception)?.message ?: DEFAULTERRORMESSAGE
                        )
                    )
                }
                401 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message = exception.message()
                        )
                    )
                }
                else -> emit(
                    Resource.error(
                        data = null,
                        message = DEFAULTERRORMESSAGE
                    )
                )
            }
        }
    }

    // Password
    fun resetPassword(
        email: String

    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val dataModel = AccountRepository.resetPassword(
                email
            )
            emit(
                Resource.success(
                    data = true
                )
            )
        } catch (exception: HttpException){
            when(exception.code()){
                400 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message =  errorHTTP400Handler(exception)?.message ?: DEFAULTERRORMESSAGE
                        )
                    )
                }
                401 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message = exception.message()
                        )
                    )
                }
                else -> emit(
                    Resource.error(
                        data = null,
                        message = DEFAULTERRORMESSAGE
                    )
                )
            }
        }
    }

    fun  controlResetCode(email: String, resetCode: String)
            = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val dataModel = AccountRepository.controlResetCode(
                email, resetCode
            )
            emit(
                Resource.success(

                    true
                )
            )
        } catch (exception: HttpException){
            when(exception.code()){
                400 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message =  errorHTTP400Handler(exception)?.message ?: DEFAULTERRORMESSAGE
                        )
                    )
                }
                401 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message = exception.message()
                        )
                    )
                }
                else -> emit(
                    Resource.error(
                        data = null,
                        message = DEFAULTERRORMESSAGE
                    )
                )
            }
        }
    }

    fun  changePassword(
        changePasswordRequestBodyModel: ChangePasswordRequestBodyModel
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val dataModel = AccountRepository.changePassword(
               changePasswordRequestBodyModel
            )
            emit(
                Resource.success(
                  true
                )
            )
        } catch (exception: HttpException){
            when(exception.code()){
                400 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message =  errorHTTP400Handler(exception)?.message ?: DEFAULTERRORMESSAGE
                        )
                    )
                }
                401 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message = exception.message()
                        )
                    )
                }
                else -> emit(
                    Resource.error(
                        data = null,
                        message = DEFAULTERRORMESSAGE
                    )
                )
            }
        }
    }

    fun  getUserInfos()
        = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val dataModel = AccountRepository.getUserInfo()
            emit(
                Resource.success(
                    gson.fromJson(
                        dataModel.charStream(), UserInfoResponseModel::class.java
                    )
                )
            )
        } catch (exception: HttpException){
            when(exception.code()){
                400 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message =  errorHTTP400Handler(exception)?.message ?: DEFAULTERRORMESSAGE
                        )
                    )
                }
                401 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message = exception.message()
                        )
                    )
                }
                else -> emit(
                    Resource.error(
                        data = null,
                        message = DEFAULTERRORMESSAGE
                    )
                )
            }
        }
    }


}