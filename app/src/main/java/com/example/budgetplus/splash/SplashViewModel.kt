package com.example.budgetplus.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetplus.model.response.UserInfoResponseModel
import com.example.budgetplus.utils.IntentUtil
import com.example.budgetplus.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class SplashViewModel(private val splashRepository: SplashRepository) : ViewModel() {

    private val _userInfo = MutableLiveData<Resource<ResponseBody>>()
    val userInfo: LiveData<Resource<ResponseBody>>
        get() = _userInfo

    private val _groupDetails = MutableLiveData<Resource<ResponseBody>>()
    val groupDetails: LiveData<Resource<ResponseBody>>
        get() = _groupDetails


    fun getUserInfo() {
        //_userInfo.postValue(Resource.loading(data= null))
        viewModelScope.launch(Dispatchers.IO) {
            _userInfo.postValue(splashRepository.getUserInfos())
        }
    }

    fun getGroupDetails(){
        viewModelScope.launch(Dispatchers.IO) {
            _groupDetails.postValue(splashRepository.getGroupDetails())
        }
    }
}