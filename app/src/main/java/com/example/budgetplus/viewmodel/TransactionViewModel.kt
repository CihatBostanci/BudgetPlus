package com.example.budgetplus.viewmodel

import androidx.lifecycle.liveData
import com.example.budgetplus.model.request.AddExpenseTransactionRequestBody
import com.example.budgetplus.model.request.AddTransferTransactionRequestBodyModel
import com.example.budgetplus.model.response.GroupDetailsResponseModel
import com.example.budgetplus.repository.GroupRepository
import com.example.budgetplus.repository.TransactionRepository
import com.example.budgetplus.utils.DEFAULTERRORMESSAGE
import com.example.budgetplus.utils.IntentUtil
import com.example.budgetplus.utils.Resource
import com.example.budgetplus.utils.errorHTTP400Handler
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException

class TransactionViewModel : BaseViewModel() {


    fun addExpense(addExpenseTransactionRequestBody: AddExpenseTransactionRequestBody
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val dataModel = TransactionRepository.addExpense(addExpenseTransactionRequestBody)
            emit(
                Resource.success(true)
            )
        } catch (exception: HttpException) {
            when (exception.code()) {
                400 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message = errorHTTP400Handler(exception)?.message ?: DEFAULTERRORMESSAGE
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

    fun addTransaction(addTransferTransactionRequestBodyModel: AddTransferTransactionRequestBodyModel
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val dataModel = TransactionRepository.addTransaction(addTransferTransactionRequestBodyModel)
            emit(
                Resource.success(true)
            )
        } catch (exception: HttpException) {
            when (exception.code()) {
                400 -> {
                    emit(
                        Resource.error(
                            data = null,
                            message = errorHTTP400Handler(exception)?.message ?: DEFAULTERRORMESSAGE
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