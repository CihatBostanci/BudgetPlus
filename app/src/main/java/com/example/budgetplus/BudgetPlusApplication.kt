package com.example.budgetplus

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.example.budgetplus.di.networkModule
import com.example.budgetplus.di.repositoryModule
import com.example.budgetplus.di.viewModelModule
import com.example.budgetplus.manager.SharedPreferencesManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


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
        startKoin {
            Log.i("tag", "Koin")
            androidContext(this@BudgetPlusApplication)
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }


}