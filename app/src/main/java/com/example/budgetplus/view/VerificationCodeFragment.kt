package com.example.budgetplus.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity.apply
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.budgetplus.BudgetPlusApplication
import com.example.budgetplus.R
import com.example.budgetplus.databinding.FragmentVerificationCodeBinding
import com.example.budgetplus.manager.SharedPreferencesManager.set
import com.example.budgetplus.model.request.ConfirmRequestBodyModel
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.utils.GenericTextWatcher
import com.example.budgetplus.utils.SENDVERIFYINFO
import com.example.budgetplus.utils.Status
import com.example.budgetplus.utils.TOKEN
import com.example.budgetplus.viewmodel.AccountViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "customerEmail"
private const val ARG_PARAM2 = "token"
private const val ARG_PARAM3 = "customerPassword"

/**
 * A simple [Fragment] subclass.
 * Use the [VerificationCodeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VerificationCodeFragment : BaseFragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var customerEmail: String? = null
    private var token: String? = null
    private var customerPassword: String? = null

    //Navigation Component controller
    private lateinit var navController: NavController

    //ViewModel
    private lateinit var accountViewModel: AccountViewModel

    //View Binding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentVerificationCodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            customerEmail = it.getString(ARG_PARAM1)
            token = it.getString(ARG_PARAM2)
            customerPassword = it.getString(ARG_PARAM3)
        }
        accountViewModel = ViewModelProvider(this)[AccountViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerificationCodeBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_verification_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //NavController of Navigation Component
        navController = Navigation.findNavController(view)
        setUIInit()
    }

    private fun setUIInit() {
        //Animation Load
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        animation.duration = 2000
        val animationRotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        binding.IWVerificationPlusLogo.animation = animationRotate
        binding.TWVerificationlogoTitle.animation = animation


        val edit =
            mutableListOf(
                binding.ETVerificationCode1, binding.ETVerificationCode2,
                binding.ETVerificationCode3, binding.ETVerificationCode4,
                binding.ETVerificationCode5, binding.ETVerificationCode6
            )

        binding.ETVerificationCode1.addTextChangedListener(
            GenericTextWatcher(binding.ETVerificationCode1, edit)
        )
        binding.ETVerificationCode2.addTextChangedListener(
            GenericTextWatcher(binding.ETVerificationCode2, edit)
        )
        binding.ETVerificationCode3.addTextChangedListener(
            GenericTextWatcher(binding.ETVerificationCode3, edit)
        )
        binding.ETVerificationCode4.addTextChangedListener(
            GenericTextWatcher(binding.ETVerificationCode4, edit)
        )
        binding.ETVerificationCode5.addTextChangedListener(
            GenericTextWatcher(binding.ETVerificationCode5, edit)
        )
        binding.ETVerificationCode6.addTextChangedListener(
            GenericTextWatcher(binding.ETVerificationCode6, edit)
        )

        binding.BTNVerificationSubmit.setOnClickListener(this)
        binding.BTNSendVerificationAgain.setOnClickListener(this)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VerificationCodeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
            VerificationCodeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
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
                binding.BTNVerificationSubmit.id -> {
                    //BudgetPlusApplication.sharedPreferencesManager[TOKEN] = token
                    verifyService()
                }
                binding.BTNSendVerificationAgain.id -> {
                    sendVerifyAgain()
                }
            }
        }

    }

    private fun getLoginRequestModel(customerEmail: String, customerPassword:String) = LoginRequestBodyModel().apply {
            email = customerEmail
            password = customerPassword
    }
    private fun sendVerifyAgain() {
        customerEmail?.let {customerEmail->
            customerPassword?.let {customerPassword->
                    accountViewModel.updateVerificationCode(getLoginRequestModel(customerEmail, customerPassword)).observe(
                        viewLifecycleOwner,
                        {
                            when(it.status){

                                Status.SUCCESS -> {
                                    hide()
                                    Toast.makeText(requireContext(), SENDVERIFYINFO, Toast.LENGTH_LONG)
                                        .show()
                                }
                                Status.LOADING -> {
                                    show()}
                                Status.ERROR -> {
                                    hide()
                                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG)
                                        .show()
                                }

                            }
                        }
                    )
            }
        }
    }

    private fun getConfirmationRequestBodyModel(customerEmail: String) =
        ConfirmRequestBodyModel().apply {
            email = customerEmail
            code =
                binding.ETVerificationCode1.text.toString() + binding.ETVerificationCode2.text.toString() +
                        binding.ETVerificationCode3.text.toString() + binding.ETVerificationCode4.text.toString() +
                        binding.ETVerificationCode5.text.toString() + binding.ETVerificationCode6.text.toString()
        }


    private fun verifyService() {
        customerEmail?.let { customerEmail ->
            token?.let { token ->

                accountViewModel.accountConfirmEmail(
                    getConfirmationRequestBodyModel(customerEmail))
                    .observe(viewLifecycleOwner,
                        {
                            Log.d(LOGINFRAGMENTTAG, it.toString())
                            when (it.status) {
                                Status.ERROR -> {
                                    hide()
                                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG)
                                        .show()

                                }
                                Status.SUCCESS -> {
                                    it?.let {
                                        it.data?.let { loginSuccessReturnModel ->
                                            hide()
                                            Toast.makeText(
                                                requireContext(),
                                                it.toString(),
                                                Toast.LENGTH_LONG
                                            )
                                                .show()
                                            navController.navigate(R.id.action_verificationCodeFragment_to_groupsFragment)
                                        }
                                    }
                                }

                                Status.LOADING -> {
                                    show()
                                }
                            }

                        })
            }
        }
    }
}