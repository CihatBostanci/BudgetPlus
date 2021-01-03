package com.example.budgetplus.view

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.budgetplus.BaseActivity
import com.example.budgetplus.MainActivity
import com.example.budgetplus.databinding.FragmentModalBottomSheetBinding
import com.example.budgetplus.extensions.hide
import com.example.budgetplus.extensions.show
import com.example.budgetplus.model.StateFriendSpinnerModel
import com.example.budgetplus.model.request.AddExpenseTransactionRequestBody
import com.example.budgetplus.model.request.GroupAddRequestBodyModel
import com.example.budgetplus.model.response.GroupDetailsResponseModel
import com.example.budgetplus.model.response.GroupDetailsResponseModelItem
import com.example.budgetplus.model.response.UserInfoResponseModel
import com.example.budgetplus.utils.*
import com.example.budgetplus.view.adapter.FriendCheckListAdapter
import com.example.budgetplus.viewmodel.GroupViewModel
import com.example.budgetplus.viewmodel.TransactionViewModel
import com.example.budgetplus.viewmodel.datatransferviewmodel.GroupDetailsTransferViewModel
import com.example.budgetplus.viewmodel.datatransferviewmodel.UserInfoTransferViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    ItemListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class ModalBottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private var actionMode: String? = null
    private var param2: String? = null

    private val MODALBOTTOMSHEETTAG = "MODALBOTTOMSHEETFTAG"
    private var userInfoResponseModel = UserInfoResponseModel()

    //MVVM ViewModel
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var transactionViewModel: TransactionViewModel

    //Transfer ViewModel
    private lateinit var _userInfoTransferViewModel: UserInfoTransferViewModel
    private lateinit var _groupDetailsTransferViewModel: GroupDetailsTransferViewModel

    private var groupDetailsResponseModelLiveData = MutableLiveData<GroupDetailsResponseModelItem?>()
    private var takeGroupId: Int = 0


    private val listCheckOfFriends: MutableList<StateFriendSpinnerModel> = mutableListOf()
    private var adapter: FriendCheckListAdapter? = null


    //View Binding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentModalBottomSheetBinding? = null
    private val binding get() = _binding!!

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
            ModalBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        groupViewModel = ViewModelProvider(this)[GroupViewModel::class.java]
        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]

        _userInfoTransferViewModel =
            ViewModelProvider(requireActivity())[UserInfoTransferViewModel::class.java]
        _userInfoTransferViewModel._userInfoResponseModel.observe(this, _userObserver)

        _groupDetailsTransferViewModel =
            ViewModelProvider(requireActivity())[GroupDetailsTransferViewModel::class.java]


        arguments?.let {
            actionMode = it.getString(FROM)
            val groupDetailsResponseModel: GroupDetailsResponseModelItem? =
                it.getSerializable(TRANSFER_GROUPS_FRIEND_LIST) as? GroupDetailsResponseModelItem
            groupDetailsResponseModelLiveData.value = groupDetailsResponseModel
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModalBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_add_expense_modal_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUIInit()
    }

    private fun setUIInit() {


        actionMode?.let {
            when (it) {
                CREATE_A_GROUP_ACTION -> {
                    setUIInitForCreateAGroup()
                }
                ADD_EXPENSE_ACTION -> {
                    setUIInitForAddExpense()
                }
                else -> {
                }
            }
        }
    }

    private fun setUIInitForCreateAGroup() {
        binding.CLAddExpense.hide()
        binding.CLCreateAGroup.show()
        binding.BTNCreateAGroup.setOnClickListener(this)
    }

    private fun setUIInitForAddExpense() {
        binding.CLAddExpense.show()
        binding.CLCreateAGroup.hide()
        binding.BTNAddExpense.setOnClickListener(this)

        groupDetailsResponseModelLiveData.observe(viewLifecycleOwner, _adapterObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                binding.BTNAddExpense.id -> addExpenseService()
                binding.BTNCreateAGroup.id -> createAGroupService()
            }
        }
    }

    private fun getGroupAddRequestBodyModel() = GroupAddRequestBodyModel(
        binding.ETCreateAGroupDescription.text.toString(),
        binding.ETGroupName.text.toString(),
        binding.SPCreateAGroupMoneyChoose.selectedItem.toString(),
        userInfoResponseModel.userId,
        binding.ETCreateAGroupBudget.text.toString().toDouble()
    )

    private fun createAGroupService() {
        Log.d(MODALBOTTOMSHEETTAG, "create triggered.")
        if (checkGroupValidation())
            groupViewModel.addGroup(getGroupAddRequestBodyModel())
                .observe(viewLifecycleOwner, _createAGroupObserver)
    }


    private fun checkGroupValidation(): Boolean {
        var validationFlag = true


        if (binding.ETGroupName.text.toString().isEmpty()) {
            binding.TILCreateAGroupName.error = GROUP_NAME_ERROR_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILCreateAGroupName.isErrorEnabled = false
        }

        if (binding.ETCreateAGroupDescription.text.toString().isEmpty()) {
            binding.TILCreateAGroupDescription.error = GROUP_DESCRIPTION_ERROR_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILCreateAGroupDescription.isErrorEnabled = false
        }
        if(binding.ETCreateAGroupBudget.text.toString().isEmpty()){
            binding.TILCreateAGroupBudget.error = GROUP_BUDGET_ERROR_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILCreateAGroupBudget.isErrorEnabled = false
        }

        return validationFlag
    }

    private fun getAddExpenseTransactionRequestBody() = AddExpenseTransactionRequestBody(
              whoAdded = userInfoResponseModel.userId,
              amount = binding.ETAddExpense.text.toString().toDouble(),
              category = binding.SPAddExpenseCategoryList.selectedItem.toString(),
              description = binding.ETAddExpenseDescription.text.toString(),
              groupId = takeGroupId,
              transactionType = "TRY",
              relatedUserIds = getFriendCheckList()
    )


    private fun getFriendCheckList(): MutableList<Int> {
        var bodyList = mutableListOf<Int>()
        adapter?.let { adapterNonNull ->
            for (i in adapterNonNull.retriveList())
                if (i.isSelected && i.userId != 0)
                    bodyList.add(i.userId)

        }
        return bodyList
    }

    private fun checkAddExpenseValidation(): Boolean {
        var validationFlag = true
        if (binding.ETAddExpenseGroupName.text.toString().isEmpty()) {
            binding.TILAddExpenseGroupName.error = GROUP_NAME_ERROR_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILAddExpenseGroupName.isErrorEnabled = false
        }
        if (binding.ETAddExpenseDescription.text.toString().isEmpty()) {
            binding.TILAddExpenseDescription.error = ADD_EXPENSE_DESCRIPTION_ERROR_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILAddExpenseDescription.isErrorEnabled = false
        }
        if (binding.ETAddExpense.text.toString().isEmpty()) {
            binding.TILAddExpense.error = ADD_EXPENSE_ERROR_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILAddExpense.isErrorEnabled = false
        }


        return validationFlag

    }


    private fun addExpenseService() {
        Log.d(MODALBOTTOMSHEETTAG, "add triggered.")
        if (checkAddExpenseValidation())
            transactionViewModel.addExpense(getAddExpenseTransactionRequestBody())
                .observe(viewLifecycleOwner, _addExpenseTransactionObserver)
    }

    //Observers
    private val _userObserver = Observer<UserInfoResponseModel> {
        userInfoResponseModel = it
    }

    private val _createAGroupObserver = Observer<Resource<Boolean>> {
        when (it.status) {
            Status.ERROR -> {
                (requireActivity() as MainActivity).hide()
                (requireActivity() as BaseActivity).showToast(it.message ?: ERROR_MESSAGE)
                this.dismiss()
            }
            Status.LOADING -> {
                (requireActivity() as MainActivity).show()
            }
            Status.SUCCESS -> {
                (requireActivity() as BaseActivity).hide()
                (requireActivity() as BaseActivity).showToast(SUCCESS_MESSAGE)
                this.dismiss()
            }
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

    private val _adapterObserver = Observer<GroupDetailsResponseModelItem?> {

        binding.ETAddExpenseGroupName.isFocusable = false
        binding.ETAddExpenseGroupName.isClickable = true
        binding.ETAddExpenseGroupName.setText(it.name)

        val spinnerFriendList = binding.SPAddExpenseGroupFriendList
        val spinnerCategoryList = binding.SPAddExpenseCategoryList
        val mutableCategoryNames = mutableListOf<String>()

        //Load Category
        if (it != null) {
            takeGroupId = it.groupId
            mutableCategoryNames.clear()
            for (i in it.expenseCategories) {
                mutableCategoryNames.add(i.name)
            }
            val adapterCategoryList = ArrayAdapter(
                requireContext(),
                R.layout.simple_spinner_item, mutableCategoryNames
            )
            spinnerCategoryList.adapter = adapterCategoryList
        }

        //Load Friend Check List
        if (it != null && it.userInfos.size > 0) {
            listCheckOfFriends.add(
                StateFriendSpinnerModel(
                    title = SPINNER_TITLE_ITEM,
                    isSelected = false,
                    userId = 0
                )
            )
            for (i in 0 until it.userInfos.size) {
                val stateVO = StateFriendSpinnerModel().apply {
                    title = it.userInfos[i].firstName + " " + it.userInfos[i].lastName
                    isSelected = true
                    userId = it.userInfos[i].id
                }
                listCheckOfFriends.add(stateVO)
            }
            context?.let {
                adapter = FriendCheckListAdapter(
                    it, 0,
                    listCheckOfFriends
                )
                spinnerFriendList.adapter = adapter
            }
        }
    }
}