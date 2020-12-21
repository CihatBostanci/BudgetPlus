package com.example.budgetplus.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.budgetplus.MainActivity
import com.example.budgetplus.R
import com.example.budgetplus.utils.IOnBackPressed
import com.example.budgetplus.utils.ProgressDisplay
import com.example.budgetplus.utils.connectionHubUrl
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import java.lang.Exception

const val BASETAG = "BASEFRATAG"

abstract class BaseFragment: Fragment(), ProgressDisplay, IOnBackPressed {


    private lateinit var hubConnection: HubConnection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TextView(activity).apply {
            setText(R.string.hello_blank_fragment)
        }
    }
    fun setCreateHubConnection() {
        hubConnection = HubConnectionBuilder.create(connectionHubUrl).build()
        try {
            hubConnection.start()
            Log.d(BASETAG, "Connection success")

        } catch (e: Exception) {
            Log.d(BASETAG, "Connection failed:  $e")
            throw e
        }
    }

    override fun show() {
        if (requireActivity() is ProgressDisplay) {
            (activity as ProgressDisplay?)!!.show()
        }
    }

    override fun hide() {
        if (requireActivity() is ProgressDisplay) {
            (activity as ProgressDisplay?)!!.hide()
        }
    }

    override fun onBackPressed(): Boolean {
        requireActivity().onBackPressed()
        return true
    }

    fun getHubConnection() = hubConnection

    fun showToast(message:String?){
        (requireActivity() as MainActivity).showToast(message)
    }
}