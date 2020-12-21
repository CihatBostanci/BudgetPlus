package com.example.budgetplus.viewmodel

import androidx.lifecycle.liveData
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.model.response.GroupDetailsResponseModel
import com.example.budgetplus.model.response.LoginSuccessResponseModel
import com.example.budgetplus.repository.AccountRepository
import com.example.budgetplus.repository.GroupRepository
import com.example.budgetplus.utils.DEFAULTERRORMESSAGE
import com.example.budgetplus.utils.IntentUtil
import com.example.budgetplus.utils.Resource
import com.example.budgetplus.utils.errorHTTP400Handler
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException

class GroupViewModel: BaseViewModel() {


    fun getGroupDetails() = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val dataModel = GroupRepository.getGroupDetails()
            emit(
                Resource.success(
                    IntentUtil.gson.fromJson(
                        dataModel.charStream(), GroupDetailsResponseModel::class.java
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