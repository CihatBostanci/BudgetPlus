package com.example.budgetplus.view

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.budgetplus.BaseActivity
import com.example.budgetplus.MainActivity
import com.example.budgetplus.databinding.FragmentTransactionTransferModalBottomSheetBinding
import com.example.budgetplus.model.request.AddTransferTransactionRequestBodyModel
import com.example.budgetplus.model.request.TransactionRelatedUser
import com.example.budgetplus.model.response.GroupDetailsResponseModelItem
import com.example.budgetplus.model.response.UserInfoResponseModel
import com.example.budgetplus.utils.*
import com.example.budgetplus.viewmodel.TransactionViewModel
import com.example.budgetplus.viewmodel.datatransferviewmodel.UserInfoTransferViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TransactionTransferModalBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransactionTransferModalBottomSheetFragment : BottomSheetDialogFragment(),
    View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private val TRANSACTIONTRANSFERMODALBOTTOMSHEETFRAGMENT = "TTRANSBMSTAG"

    private var groupDetailsResponseModelLiveData =
        MutableLiveData<GroupDetailsResponseModelItem?>()

    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var _userInfoTransferViewModel: UserInfoTransferViewModel

    private var userInfoResponseModel = UserInfoResponseModel()
    private var takeGroupId: Int = 0
    private val mutableFriendList = mutableListOf<Int>()


    //View Binding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentTransactionTransferModalBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        _userInfoTransferViewModel =
            ViewModelProvider(requireActivity())[UserInfoTransferViewModel::class.java]
        _userInfoTransferViewModel._userInfoResponseModel.observe(this, _userObserver)

        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]


        arguments?.let {
            if (it.getSerializable(TRANSFER_GROUPS_FRIEND_LIST) as GroupDetailsResponseModelItem? != null) {
                val groupDetailsResponseModel: GroupDetailsResponseModelItem =
                    it.getSerializable(TRANSFER_GROUPS_FRIEND_LIST) as GroupDetailsResponseModelItem
                groupDetailsResponseModelLiveData.value = groupDetailsResponseModel
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionTransferModalBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUIInit()
    }

    private fun setUIInit() {
        groupDetailsResponseModelLiveData.observe(viewLifecycleOwner, _groupInfoObserver)
        binding.BTNTransferTransaction.setOnClickListener(this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TransactionTransferModalBottomSheetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TransactionTransferModalBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                binding.BTNTransferTransaction.id -> transactionTransferAction()
            }
        }
    }

    private fun checkTransactionValidation(): Boolean {
        var validationFlag = true

        if (binding.ETTransactionGroupDescription.text.toString().isEmpty()) {
            binding.TILTransactionTransferDescription.error = GROUP_DESCRIPTION_ERROR_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILTransactionTransferDescription.isErrorEnabled = false
        }
        if (binding.ETTransferExpense.text.toString().isEmpty()) {
            binding.TILTransferTransactionExpense.error = GROUP_DESCRIPTION_ERROR_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILTransferTransactionExpense.isErrorEnabled = false
        }
        if (binding.ETTransactionGroupDescription.text.toString().isEmpty()) {
            binding.TILTransactionTransferDescription.error = GROUP_DESCRIPTION_ERROR_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILTransactionTransferDescription.isErrorEnabled = false
        }

        return validationFlag
    }

    private fun transactionTransferAction() {
        if (checkTransactionValidation()) {
            transactionViewModel.addTransaction(getTransactionRequestBody())
                .observe(viewLifecycleOwner, _addExpenseTransactionObserver)
        }
    }

    private fun getTransactionRequestBody() = AddTransferTransactionRequestBodyModel(
        binding.ETTransactionGroupDescription.text.toString(),
        groupId = takeGroupId,
        transactionRelatedUsers = fillRelatedUsers(),
        whoAdded = userInfoResponseModel.userId
    )

    private fun fillRelatedUsers(): MutableList<TransactionRelatedUser> {
        val mutableList = mutableListOf<TransactionRelatedUser>()
        val transactionRelatedUser = TransactionRelatedUser()
        transactionRelatedUser.amount = binding.ETTransferExpense.text.toString().toDouble()
        transactionRelatedUser.relatedUserId =
            mutableFriendList[binding.SPTransferTransactionGroupFriendList.selectedItemPosition]
        mutableList.add(transactionRelatedUser)
        return mutableList
    }

    //Observers
    private val _userObserver = Observer<UserInfoResponseModel> {
        userInfoResponseModel = it
    }

    private val _groupInfoObserver = Observer<GroupDetailsResponseModelItem?> {
        val mutableFriendNames = mutableListOf<String>()

        val spinnerTransferList = binding.SPTransferTransactionGroupFriendList
        //Load Friends
        if (it != null) {
            takeGroupId = it.groupId
            binding.ETTransferTransactionGroupName.isFocusable = false
            binding.ETTransferTransactionGroupName.isClickable = true
            binding.ETTransferTransactionGroupName.setText(it.name)

            mutableFriendNames.clear()
            mutableFriendNames.clear()
            for (i in it.userInfos) {
                if (i.id != userInfoResponseModel.userId) {
                    mutableFriendNames.add(i.firstName + " " + i.lastName)
                    mutableFriendList.add(i.id)

                }

            }

            val adapterCategoryList = ArrayAdapter(
                requireContext(),
                R.layout.simple_spinner_item, mutableFriendNames
            )
            spinnerTransferList.adapter = adapterCategoryList
        }
    }
    private val _addExpenseTransactionObserver = Observer<Resource<Boolean>> {
        when (it.status) {
            Status.ERROR -> {
                (requireActivity() as MainActivity).hide()
                (requireActivity() as BaseActivity).showToast(it.message ?: ERROR_MESSAGE)
                this.dismiss()

            }
            Status.LOADING -> {
                (requireActivity() as BaseActivity).show()
            }
            Status.SUCCESS -> {
                (requireActivity() as BaseActivity).hide()
                (requireActivity() as BaseActivity).showToast(SUCCESS_MESSAGE)
                this.dismiss()

            }
        }
    }
}