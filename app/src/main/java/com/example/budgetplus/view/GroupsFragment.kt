package com.example.budgetplus.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.budgetplus.MainActivity
import com.example.budgetplus.R
import com.example.budgetplus.databinding.FragmentGroupsBinding
import com.example.budgetplus.model.response.GroupDetailsResponseModel
import com.example.budgetplus.model.response.UserInfoResponseModel
import com.example.budgetplus.utils.ADD_EXPENSE_ACTION
import com.example.budgetplus.utils.CREATE_A_GROUP_ACTION
import com.example.budgetplus.utils.FROM
import com.example.budgetplus.view.adapter.GroupsAdapterWithViewPager
import com.example.budgetplus.viewmodel.AccountViewModel
import com.example.budgetplus.viewmodel.GroupViewModel
import com.example.budgetplus.viewmodel.SocketViewModel
import com.example.budgetplus.viewmodel.datatransferviewmodel.GroupDetailsTransferViewModel
import com.example.budgetplus.viewmodel.datatransferviewmodel.UserInfoTransferViewModel
import com.microsoft.signalr.HubConnection
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

    private var param1: String? = null
    private var param2: String? = null

    private val GROUPSFRAGMENTTAG = "GROUPSTAG"

    //For MVVM ViewModel
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var groupViewModel: GroupViewModel

    //Socket MVVM ViewModel
    private lateinit var socketViewModel: SocketViewModel

    //Transfer ViewModel
    private lateinit var _userInfoTransferViewModel: UserInfoTransferViewModel
    private lateinit var _groupDetailsTransferViewModel: GroupDetailsTransferViewModel

    //Navigation Component controller
    private lateinit var navController: NavController

    private var groupDetailsResponseModelLiveData =  MutableLiveData<GroupDetailsResponseModel>()

    val categories = listOf(
        "Group 1",
        "Group 2",
        "Group 3",
        "Group 4",
        "Group 5",
    )
    //Hub Connection
    private var hubConnection = MutableLiveData<HubConnection>()

    //View Binding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentGroupsBinding? = null
    private val binding get() = _binding!!

    private var chat: String = ""
    private var message: String = ""
    private var groupName = "Group1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accountViewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        groupViewModel = ViewModelProvider(this)[GroupViewModel::class.java]
        socketViewModel = ViewModelProvider(this)[SocketViewModel::class.java]

        _userInfoTransferViewModel =
            ViewModelProvider(requireActivity())[UserInfoTransferViewModel::class.java]
        _groupDetailsTransferViewModel =
            ViewModelProvider(requireActivity())[GroupDetailsTransferViewModel::class.java]

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
        setCreateHubConnection()
        setBroadCastListener()
        setUIInit()

    }

    private fun setCreateHubConnection() {
        socketViewModel.getHubConnection().observe(viewLifecycleOwner, _socketObserver)
    }

    private fun setUIInit() {

        (requireActivity() as MainActivity).setBottomNavigationVisibility(viewVisible = true)


        _userInfoTransferViewModel._userInfoResponseModel.observe(
            viewLifecycleOwner,
            _userInfoObserver
        )
        _groupDetailsTransferViewModel._groupDetailsResponseModel.observe(
            viewLifecycleOwner,
            _groupDetailsObserver
        )
        setViewPagerAdapter()
        binding.BTNJoinGroup.setOnClickListener(this)
        binding.BTNSendGroup.setOnClickListener(this)

        binding.BTNTransferTransaction.setOnClickListener(this)
        binding.FABCreateAGroupAction.setOnClickListener(this)
        binding.FABAddExpenseAction.setOnClickListener(this)
        binding.FABShareLinkAction.setOnClickListener(this)


    }

    private fun setViewPagerAdapter() {

        groupDetailsResponseModelLiveData.observe(viewLifecycleOwner,
            {
            val adapterWithViewPager = GroupsAdapterWithViewPager()
            adapterWithViewPager.setItem(it)
            binding.VPForGroup.adapter = adapterWithViewPager
        })


    }

    private fun setBroadCastListener() {
        val CLIENT_METHOD_BROADAST_MESSAGE = "ReceiveMessage"
        hubConnection.observe(viewLifecycleOwner,
            {
                it.on(
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
            })
    }

    private fun chatMessage(message: String) {
        chat += message + "\n"
        binding.TWChannel.text = chat
    }

    private fun setJoinGroup(groupName: String) {
        //hubConnection.invoke( Void.class,"JoinGroup", groupName)
        hubConnection.observe(viewLifecycleOwner,
            {
                it.invoke(Void::class.java, "JoinGroup", groupName)
            })
    }

    private fun setSendGroup(message: String) {
        try {
            if (groupName.isNotEmpty() && message.isNotEmpty()) {
                hubConnection.observe(viewLifecycleOwner,
                    {
                        it.invoke(
                            Void::class.java,
                            "DirectMessageToGroup",
                            groupName,
                            message
                        )
                    })
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
                binding.BTNTransferTransaction.id -> transferTransactionAction()
                binding.BTNJoinGroup.id -> {
                    groupName = binding.ETJoinGroup.text.toString()
                    setJoinGroup(groupName)
                }
                binding.BTNSendGroup.id -> {
                    message = binding.ETSendGroup.text.toString()
                    setSendGroup(message)
                }
                binding.FABCreateAGroupAction.id -> addGroupAction()
                binding.FABAddExpenseAction.id -> addExpenseAction()
                binding.FABShareLinkAction.id -> shareLinkAction()
            }
        }
    }

    private fun transferTransactionAction() {
        Log.d(GROUPSFRAGMENTTAG, "send transfer pressed")
        navController.navigate(R.id.action_groupsFragment_to_transactionTransferModalBottomSheetFragment)
    }

    private fun shareLinkAction() {
        Log.d(GROUPSFRAGMENTTAG, "share Link pressed")
    }

    private fun addExpenseAction() {
        Log.d(GROUPSFRAGMENTTAG, "add expense pressed")
        val fromActionBundle =
            bundleOf(
                FROM to ADD_EXPENSE_ACTION,
            )
        navController.navigate(
            R.id.action_groupsFragment_to_modalBottomSheetFragment,
            fromActionBundle
        )

    }

    private fun addGroupAction() {
        Log.d(GROUPSFRAGMENTTAG, "Create a group pressed")
        val fromActionBundle =
            bundleOf(
                FROM to CREATE_A_GROUP_ACTION,
            )
        navController.navigate(
            R.id.action_groupsFragment_to_modalBottomSheetFragment,
            fromActionBundle
        )

    }



    //Observers
    private val _userInfoObserver = Observer<UserInfoResponseModel> {
        Log.d(GROUPSFRAGMENTTAG, it.toString())


    }

    private val _groupDetailsObserver = Observer<GroupDetailsResponseModel> {
        Log.d(GROUPSFRAGMENTTAG, it.toString())

            groupDetailsResponseModelLiveData.value = it
    }

    private val _socketObserver = Observer<HubConnection> {
        hubConnection.postValue(it)
    }

}