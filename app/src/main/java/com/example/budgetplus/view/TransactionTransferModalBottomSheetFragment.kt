package com.example.budgetplus.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.budgetplus.databinding.FragmentTransactionTransferModalBottomSheetBinding
import com.example.budgetplus.model.StateFriendSpinnerModel
import com.example.budgetplus.model.response.GroupDetailsResponseModel
import com.example.budgetplus.utils.FROM
import com.example.budgetplus.utils.TRANSFER_GROUPS_FRIEND_LIST
import com.example.budgetplus.view.adapter.FriendCheckListAdapter
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
class TransactionTransferModalBottomSheetFragment :  BottomSheetDialogFragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private val TRANSACTIONTRANSFERMODALBOTTOMSHEETFRAGMENT = "TTRANSBMSTAG"

    private var groupDetailsResponseModelLiveData =  MutableLiveData<GroupDetailsResponseModel?>()

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

        arguments?.let {
            if( it.getParcelable<GroupDetailsResponseModel>(TRANSFER_GROUPS_FRIEND_LIST) as GroupDetailsResponseModel? !=null){
                val groupDetailsResponseModel:GroupDetailsResponseModel = it.getParcelable<GroupDetailsResponseModel>(TRANSFER_GROUPS_FRIEND_LIST) as GroupDetailsResponseModel
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

    }
}