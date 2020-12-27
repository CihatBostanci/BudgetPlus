package com.example.budgetplus.repository

import android.util.Log
import com.example.budgetplus.utils.connectionHubUrl
import com.example.budgetplus.view.BASETAG
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import java.lang.Exception

object SocketRepository {

    private lateinit var hubConnection: HubConnection
    private val SOCKETREPOSITORYTAG = "SOCKETTAG"
    fun setCreateHubConnection() {

        hubConnection = HubConnectionBuilder.create(connectionHubUrl).build()
        try {
            hubConnection.start()
            Log.d(SOCKETREPOSITORYTAG, "Connection success")

        } catch (e: Exception) {
            Log.d(SOCKETREPOSITORYTAG, "Connection failed:  $e")
            throw e
        }
    }

    fun getHubConnection() = hubConnection
}