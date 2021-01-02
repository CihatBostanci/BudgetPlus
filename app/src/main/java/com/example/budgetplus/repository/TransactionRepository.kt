package com.example.budgetplus.repository

import com.example.budgetplus.model.request.AddExpenseTransactionRequestBody
import com.example.budgetplus.model.request.AddTransferTransactionRequestBodyModel

object TransactionRepository : BaseRepository() {

    private val apiTransactionService = RetrofilBuilder.apiTransactionService

    suspend fun addExpense(addExpenseTransactionRequestBody: AddExpenseTransactionRequestBody)
            = apiTransactionService.addExpense(addExpenseTransactionRequestBody  )

    suspend fun addTransaction(addTransferTransactionRequestBodyModel: AddTransferTransactionRequestBodyModel)
            = apiTransactionService.addTransfer(addTransferTransactionRequestBodyModel)
}