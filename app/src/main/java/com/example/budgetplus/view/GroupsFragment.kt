package com.example.budgetplus.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.budgetplus.BudgetPlusApplication
import com.example.budgetplus.MainActivity
import com.example.budgetplus.R
import com.example.budgetplus.databinding.FragmentGroupsBinding
import com.example.budgetplus.manager.SharedPreferencesManager.set
import com.example.budgetplus.utils.TOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupsFragment : BaseFragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //Navigation Component controller
    private lateinit var navController: NavController

    //View Binding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentGroupsBinding? = null
    private val binding get() = _binding!!

    private var chat: String = ""
    private var message: String= ""
    private var groupName = "Group1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupsBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //NavController of Navigation Component
        navController = Navigation.findNavController(view)
        setUIInit()
        setCreateHubConnection()
        setBroadCastListener()
        setTrigger()

    }
    private fun setUIInit() {
        (requireActivity() as MainActivity).setBottomNavigationVisibility(viewVisible = true)
        binding.BTNLogout.setOnClickListener(this)
    }
    private fun setBroadCastListener() {
        val CLIENT_METHOD_BROADAST_MESSAGE = "ReceiveMessage"
        getHubConnection().on(
            CLIENT_METHOD_BROADAST_MESSAGE,
            { message: String ->
                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        chatMessage(message)
                    } catch (t: Throwable) {
                        Log.d("GROUPTAG", "Failed chat message")
                    }
                }
            },
            String::class.java
        )
    }

    private fun chatMessage(message: String) {

        chat += message + "\n"
        binding.TWChannel.text = chat
    }

    private fun setTrigger() {

        binding.BTNJoinGroup.setOnClickListener(this)
        binding.BTNSendGroup.setOnClickListener(this)
    }

    private fun setJoinGroup(groupName: String) {
        //hubConnection.invoke( Void.class,"JoinGroup", groupName)
        getHubConnection().invoke(Void::class.java, "JoinGroup", groupName)
    }

    private fun setSendGroup(message: String) {
        try {
            if (groupName.isNotEmpty() && message.isNotEmpty()) {
                getHubConnection().invoke(
                    Void::class.java,
                    "DirectMessageToGroup",
                    groupName,
                    message
                )
            }

        } catch (e: Exception) {
            Log.e("GROUPTAG", e.toString())

        }

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GroupsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                binding.BTNLogout.id -> {
                    logoutAction()
                }
                binding.BTNJoinGroup.id -> {
                    groupName = binding.ETJoinGroup.text.toString()
                    setJoinGroup(groupName)
                }
                binding.BTNSendGroup.id -> {
                    message = binding.ETSendGroup.text.toString()
                    setSendGroup(message)
                }
            }
        }
    }

    private fun logoutAction() {
        (requireActivity() as MainActivity).setBottomNavigationVisibility(viewVisible = false)
        BudgetPlusApplication.sharedPreferencesManager[TOKEN] = ""
        navController.navigate(R.id.action_global_loginFragment)
    }

}