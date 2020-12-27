package com.example.budgetplus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.budgetplus.repository.SocketRepository
import com.microsoft.signalr.HubConnection

class SocketViewModel: BaseViewModel() {

    init {
        SocketRepository.setCreateHubConnection()
    }

    fun getHubConnection():LiveData<HubConnection> {
        val mutableLiveData = MutableLiveData<HubConnection>()
        mutableLiveData.postValue(SocketRepository.getHubConnection())
        return mutableLiveData
    }

}