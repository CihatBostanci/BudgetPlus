package com.example.budgetplus.repository.service

import com.example.budgetplus.model.request.AddExpenseTransactionRequestBody
import com.example.budgetplus.model.request.RegisterRequestBodyModel
import com.example.budgetplus.utils.ADD_EXPENSE
import com.example.budgetplus.utils.ADD_TRANSFER
import com.example.budgetplus.utils.GET_GROUP_DETAILS_SERVICE
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TransactionService {

    @POST(ADD_EXPENSE)
    suspend fun addExpense(
        @Body addExpenseTransactionRequestBody: AddExpenseTransactionRequestBody
    ): ResponseBody

    @POST(ADD_TRANSFER)
    suspend fun addTransfer(): ResponseBody

}