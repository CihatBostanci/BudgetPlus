package com.example.budgetplus


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.budgetplus.databinding.ActivityMainBinding
import com.example.budgetplus.repository.RetrofilBuilder
import com.example.budgetplus.utils.ERRORMESSAGE
import com.example.budgetplus.utils.IOnBackPressed
import com.example.budgetplus.view.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    //Activity View
    //private lateinit var bottomNavigationView: BottomNavigationView

    //Navigation Component controller
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInit()
    }

    private fun setInit() {
        RetrofilBuilder.getInstance(this)

        binding.bottomNavigation.setNavigationChangeListener { view , position ->
          when(view.id){
             binding.cItemGroups.id -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_groupsFragment)
             binding.cItemNotification.id -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_notificationFragment)
             binding.cItemMore.id -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_accountFragment)
        }

        }

    }

    override fun onBackPressed() {
        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        navHostFragment?.let {
            val fragment = it.childFragmentManager.fragments[0]
            if (fragment != null && fragment is IOnBackPressed) {
                when (fragment) {
                    is SignUpFragment, is ForgetPasswordFragment -> {
                        (fragment as IOnBackPressed?)?.onBackPressed()
                        findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_loginFragment)
                        //super.onBackPressed()
                    }
                    is GroupsFragment-> {}
                    is NotificationFragment -> {}
                    is AccountFragment->{}
                    else -> super.onBackPressed()
                }
            }


        }
    }

    fun showToast(message:String?){
        Toast.makeText(this, message ?: ERRORMESSAGE, Toast.LENGTH_LONG).show()
    }


    fun setBottomNavigationVisibility(viewVisible: Boolean)
            = if (viewVisible) binding.bottomNavigation.visibility = View.VISIBLE
    else binding.bottomNavigation.visibility = View.GONE

}