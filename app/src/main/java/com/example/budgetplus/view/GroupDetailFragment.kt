package com.example.budgetplus.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.budgetplus.R
import com.example.budgetplus.databinding.FragmentGroupDetailBinding
import com.example.budgetplus.databinding.FragmentLoginBinding
import com.example.budgetplus.model.response.GroupDetailsResponseModelItem
import com.example.budgetplus.utils.GROUP_DETAIL_ACTION_INFO
import com.example.budgetplus.utils.TRANSFER_GROUPS_FRIEND_LIST

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupDetailFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var groupDetailsResponseModelLiveData =
        MutableLiveData<GroupDetailsResponseModelItem?>()

    //View Binding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentGroupDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.getSerializable(GROUP_DETAIL_ACTION_INFO) as GroupDetailsResponseModelItem? != null) {
                val groupDetailsResponseModel: GroupDetailsResponseModelItem =
                    it.getSerializable(GROUP_DETAIL_ACTION_INFO) as GroupDetailsResponseModelItem
                groupDetailsResponseModelLiveData.value = groupDetailsResponseModel
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupDetailBinding.inflate(inflater, container, false)
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
        groupDetailsResponseModelLiveData.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.TWGroupDetailTitle.text = it.name
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GroupDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}