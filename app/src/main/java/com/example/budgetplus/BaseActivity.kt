package com.example.budgetplus

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetplus.utils.ProgressDisplay

abstract class BaseActivity : AppCompatActivity(), ProgressDisplay{

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        super.onStart()
        BudgetPlusApplication.getApplication().setCurrentActivity(this)
    }
    var progressBar: ProgressBar? = null
    override fun show() {
        progressBar = findViewById(R.id.progressBar)
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hide() {
        progressBar = findViewById(R.id.progressBar)
        progressBar!!.visibility = View.INVISIBLE
    }


}