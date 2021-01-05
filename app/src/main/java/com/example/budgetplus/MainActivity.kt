package com.example.budgetplus


import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.budgetplus.account.AccountFragment
import com.example.budgetplus.databinding.ActivityMainBinding
import com.example.budgetplus.forgetpassword.ForgetPasswordFragment
import com.example.budgetplus.groups.GroupsFragment
import com.example.budgetplus.notification.NotificationFragment
import com.example.budgetplus.signup.SignUpFragment
import com.example.budgetplus.utils.IOnBackPressed


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInit()
    }

    private fun setInit() {
        setBottomNavigation()

    }

    private fun setBottomNavigation() {
        binding.bottomNavigation.setNavigationChangeListener { view, _ ->
            when (view.id) {
                binding.cItemGroups.id -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_groupsFragment)
                binding.cItemNotification.id -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_notificationFragment)
                binding.cItemMore.id -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_accountFragment)
            }
        }
    }

    fun navigationSetter() {

        binding.bottomNavigation.onClick(binding.cItemGroups)
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
                    }
                    is GroupsFragment -> {
                    }
                    is NotificationFragment -> {
                    }
                    is AccountFragment -> {
                    }
                    else -> super.onBackPressed()
                }
            }


        }
    }

    fun setBottomNavigationVisibility(viewVisible: Boolean) =
        if (viewVisible) binding.bottomNavigation.visibility = View.VISIBLE
        else binding.bottomNavigation.visibility = View.GONE

}