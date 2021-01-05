package com.example.budgetplus.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _userInfo = MutableLiveData<Resource<ResponseBody>>()
    val userInfo: LiveData<Resource<ResponseBody>>
        get() = _userInfo

    private val _groupDetails = MutableLiveData<Resource<ResponseBody>>()
    val groupDetails: LiveData<Resource<ResponseBody>>
        get() = _groupDetails

    private val _authenticate = MutableLiveData<Resource<ResponseBody>>()
    val authenticate: LiveData<Resource<ResponseBody>>
        get() = _authenticate

    fun getUserInfo() {
       
        viewModelScope.launch(Dispatchers.IO) {
            _userInfo.postValue(loginRepository.getUserInfos())
        }
    }

    fun getGroupDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _groupDetails.postValue(loginRepository.getGroupDetails())
        }
    }

    fun getAuthenticate(loginRequestBodyModel: LoginRequestBodyModel) {
        _authenticate.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _authenticate.postValue(loginRepository.getAuthenticate(loginRequestBodyModel))
        }
    }

}