package com.example.budgetplus.transactionmodalbottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetplus.model.request.AddTransferTransactionRequestBodyModel
import com.example.budgetplus.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class TransactionModalBottomSheetViewModel(
    private val transactionModalBottomSheetRepository: TransactionModalBottomSheetRepository
) : ViewModel() {

    private val _transactionAdd = MutableLiveData<Resource<ResponseBody>>()
    val transactionAdd: LiveData<Resource<ResponseBody>>
        get() = _transactionAdd

    fun addTranasction(
        addTransferTransactionRequestBodyModel: AddTransferTransactionRequestBodyModel
    ) {
        _transactionAdd.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _transactionAdd.postValue(
                transactionModalBottomSheetRepository
                    .addTransaction(addTransferTransactionRequestBodyModel)
            )
        }
    }

}