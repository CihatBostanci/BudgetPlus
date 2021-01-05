package com.example.budgetplus.forgetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetplus.model.request.ChangePasswordRequestBodyModel
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class ForgetPasswordViewModel(
    private val forgetPasswordRepository: ForgetPasswordRepository
) : ViewModel() {

    private val _resetPassword = MutableLiveData<Resource<ResponseBody>>()
    val resetPassword: LiveData<Resource<ResponseBody>>
        get() = _resetPassword

    private val _controlResetCode = MutableLiveData<Resource<ResponseBody>>()
    val controlResetCode: LiveData<Resource<ResponseBody>>
        get() = _controlResetCode

    private val _changePassword = MutableLiveData<Resource<ResponseBody>>()
    val changePassword: LiveData<Resource<ResponseBody>>
        get() = _changePassword

    fun resetPassword(email: String){
        _resetPassword.postValue(Resource.loading(data= null))
        viewModelScope.launch(Dispatchers.IO) {
            _resetPassword.postValue(forgetPasswordRepository.resetPassword(email))
        }
    }

    fun controlResetCode(email: String, resetCode: String) {
        _controlResetCode.postValue(Resource.loading(data= null))
        viewModelScope.launch(Dispatchers.IO) {
            _controlResetCode.postValue(forgetPasswordRepository.controlResetCode(email,resetCode))
        }
    }

    fun changePassword(
        changePasswordRequestBodyModel: ChangePasswordRequestBodyModel
    )  {
        _changePassword.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _changePassword.postValue(forgetPasswordRepository.changePassword(changePasswordRequestBodyModel))
        }
    }

}