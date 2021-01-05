package com.example.budgetplus.transactionmodalbottomsheet

import com.example.budgetplus.di.safeApiCall
import com.example.budgetplus.model.request.AddTransferTransactionRequestBodyModel
import com.example.budgetplus.service.TransactionService

class TransactionModalBottomSheetRepository(private val transactionService: TransactionService) {

    suspend fun addTransaction(addTransferTransactionRequestBodyModel: AddTransferTransactionRequestBodyModel) =
        safeApiCall {
            transactionService.addTransfer(addTransferTransactionRequestBodyModel)
        }

}