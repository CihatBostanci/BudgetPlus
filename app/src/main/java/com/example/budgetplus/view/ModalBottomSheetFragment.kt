package com.example.budgetplus.view

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.budgetplus.BudgetPlusApplication
import com.example.budgetplus.MainActivity
import com.example.budgetplus.databinding.FragmentModalBottomSheetBinding
import com.example.budgetplus.extensions.*
import com.example.budgetplus.manager.SharedPreferencesManager.set
import com.example.budgetplus.model.request.GroupAddRequestBodyModel
import com.example.budgetplus.model.response.UserInfoResponseModel
import com.example.budgetplus.utils.*
import com.example.budgetplus.viewmodel.GroupViewModel
import com.example.budgetplus.viewmodel.datatransferviewmodel.GroupDetailsTransferViewModel
import com.example.budgetplus.viewmodel.datatransferviewmodel.UserInfoTransferViewModel


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

    //Transfer ViewModel
    private lateinit var _userInfoTransferViewModel: UserInfoTransferViewModel
    private lateinit var _groupDetailsTransferViewModel: GroupDetailsTransferViewModel


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

        _userInfoTransferViewModel =
            ViewModelProvider(requireActivity())[UserInfoTransferViewModel::class.java]
        _groupDetailsTransferViewModel =
            ViewModelProvider(requireActivity())[GroupDetailsTransferViewModel::class.java]


        arguments?.let {
            actionMode = it.getString(FROM)
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

        _userInfoTransferViewModel._userInfoResponseModel.observe(viewLifecycleOwner, _userObserver)

        actionMode?.let {
            when (it) {
                CREATE_A_GROUP_ACTION -> {
                    binding.CLAddExpense.hide()
                    binding.CLCreateAGroup.show()
                    binding.BTNCreateAGroup.setOnClickListener(this)

                }
                ADD_EXPENSE_ACTION -> {
                    binding.CLAddExpense.show()
                    binding.CLCreateAGroup.hide()
                    binding.BTNAddExpense.setOnClickListener(this)
                }
                else -> {
                }
            }
        }
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
        userInfoResponseModel.userId
    )

    private fun createAGroupService() {
        Log.d(MODALBOTTOMSHEETTAG, "create triggered.")
        if (checkGroupValidation())
            groupViewModel.addGroup(getGroupAddRequestBodyModel())
                .observe(viewLifecycleOwner, _createAGroupObserver)
    }

    private fun groupNameValidation(): Boolean {
        var validationFlag = true

        if (binding.ETGroupName.text.toString().isEmpty()) {
            binding.TILCreateAGroupName.error = GROUP_NAME_ERROR_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILCreateAGroupName.isErrorEnabled = false
        }
        return validationFlag
    }

    private fun checkGroupValidation(): Boolean {
        var validationFlag = true

        if (!groupNameValidation()) return false

        if (binding.ETCreateAGroupDescription.text.toString().isEmpty()) {
            binding.TILCreateAGroupDescription.error = GROUP_DESCRIPTION_ERROR_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILCreateAGroupDescription.isErrorEnabled = false
        }

        return validationFlag
    }

    private fun addExpenseService() {
        Log.d(MODALBOTTOMSHEETTAG, "add triggered.")
    }

    //Observers
    private val _userObserver = Observer<UserInfoResponseModel> {
        userInfoResponseModel = it
    }

    private val _createAGroupObserver = Observer<Resource<Boolean>> {
        when (it.status) {
            Status.ERROR -> {
                (requireActivity() as MainActivity).hide()
                Toast.makeText(requireContext(), it.message ?: ERROR_MESSAGE,
                    Toast.LENGTH_LONG).show()
            }
            Status.LOADING -> {
                (requireActivity() as MainActivity).show()
            }
            Status.SUCCESS -> {
                (requireActivity() as MainActivity).hide()
                Toast.makeText(requireContext(), SUCCESS_MESSAGE,
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}