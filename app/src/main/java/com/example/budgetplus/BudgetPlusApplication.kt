package com.example.budgetplus

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import com.example.budgetplus.manager.SharedPreferencesManager


val sharedPreferencesManager: SharedPreferences by lazy {
    BudgetPlusApplication.sharedPreferencesManager
}

class BudgetPlusApplication : Application() {


    companion object {

        lateinit var sharedPreferencesManager: SharedPreferences

        private var mCurrentActivity: Activity? = null

        //private static SegmentifyManager segmentifyManager;
        fun getApplication(): Companion {
            return this
        }

        fun setCurrentActivity(mCurrentActivity: Activity) {
            this.mCurrentActivity = mCurrentActivity
        }

        fun getCurrentActivity() = this.mCurrentActivity

    }

    override fun onCreate() {
        sharedPreferencesManager = SharedPreferencesManager.defaultPrefs(applicationContext)
        super.onCreate()
    }


}