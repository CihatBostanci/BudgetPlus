package com.example.budgetplus.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.budgetplus.R
import com.example.budgetplus.databinding.FragmentForgetPasswordBinding
import com.example.budgetplus.model.request.ChangePasswordRequestBodyModel
import com.example.budgetplus.utils.*
import com.example.budgetplus.viewmodel.AccountViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForgetPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgetPasswordFragment : BaseFragment(), View.OnClickListener, IOnBackPressed {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //Navigation Component controller
    private lateinit var navController: NavController

    private lateinit var accountViewModel: AccountViewModel

    private var emailStr = ""
    private var codeStr = ""

    //View Binding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        accountViewModel = ViewModelProvider(this)[AccountViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_forget_password, container, false)
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
        binding.IWForgetPasswordPlusLogo.animation = animationRotate
        binding.TWForgetPasswordlogoTitle.animation = animation

        val editForgetList =
            mutableListOf(
                binding.ETForgetVerificationCode1, binding.ETForgetVerificationCode2,
                binding.ETForgetVerificationCode3, binding.ETForgetVerificationCode4,
                binding.ETForgetVerificationCode5, binding.ETForgetVerificationCode6
            )

        binding.ETForgetVerificationCode1.addTextChangedListener(
            GenericTextWatcher(binding.ETForgetVerificationCode1, editForgetList)
        )
        binding.ETForgetVerificationCode2.addTextChangedListener(
            GenericTextWatcher(binding.ETForgetVerificationCode2, editForgetList)
        )
        binding.ETForgetVerificationCode3.addTextChangedListener(
            GenericTextWatcher(binding.ETForgetVerificationCode3, editForgetList)
        )
        binding.ETForgetVerificationCode4.addTextChangedListener(
            GenericTextWatcher(binding.ETForgetVerificationCode4, editForgetList)
        )
        binding.ETForgetVerificationCode5.addTextChangedListener(
            GenericTextWatcher(binding.ETForgetVerificationCode5, editForgetList)
        )
        binding.ETForgetVerificationCode6.addTextChangedListener(
            GenericTextWatcher(binding.ETForgetVerificationCode6, editForgetList)
        )

        binding.BTNForgetSubmit.setOnClickListener(this)

    }

    private fun checkValidation(): Boolean {
        var validationFlag = true
        if (!EMAIL_ADDRESS_PATTERN.matcher(binding.ETForgetEmail.text.toString()).matches()) {
            binding.TILForgetEmail.error = EMAIL_INVALID_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILForgetEmail.isErrorEnabled = false
        }

        return validationFlag
    }

    private fun checkValidationPassword(): Boolean {
        var validationFlag = true
        if (!EMAIL_ADDRESS_PATTERN.matcher(binding.ETForgetEmail.text.toString()).matches()) {
            binding.TILForgetEmail.error = EMAIL_INVALID_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILForgetEmail.isErrorEnabled = false
        }

        if (binding.ETForgetPassword.text?.length !in 5..12 &&
            !isValidPassword(binding.ETForgetPassword.text.toString())
        ) {
            binding.TILForgetPassword.error = PASSWORD_INVALID_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILForgetPassword.isErrorEnabled = false
        }
        if (binding.ETForgetConfirmationPassword.text.toString().isEmpty()
            || binding.ETForgetConfirmationPassword.text.toString()
            != binding.ETForgetPassword.text.toString()
        ) {
            binding.TILForgetConfirmationPassword.error =
               FORGET_PASSWORD_ERROR_MESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILForgetConfirmationPassword.isErrorEnabled = false
        }

        return validationFlag

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForgetPasswordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForgetPasswordFragment().apply {
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
            when (v.id) {
                binding.BTNForgetSubmit.id -> {
                    if(binding.CLForgetVerificationCode.visibility == View.GONE
                        &&binding.TILForgetPassword.visibility == View.GONE)
                        resetPasswordService()
                    else if (binding.CLForgetVerificationCode.visibility == View.VISIBLE )
                        controlResetCodeService()
                    else if(binding.CLForgetVerificationCode.visibility == View.GONE
                        && binding.TILForgetPassword.visibility == View.VISIBLE)
                            changePasswordService()
                }
            }
        }
    }


    private fun getChangePasswordRequestBodyModel() = ChangePasswordRequestBodyModel().apply {
        this.code  = codeStr
        email = emailStr
        password = binding.ETForgetPassword.text.toString()
        confirmPassword = binding.ETForgetConfirmationPassword.text.toString()
    }


    private fun resetPasswordService() {
        if (checkValidation()) {

            accountViewModel.resetPassword(binding.ETForgetEmail.text.toString())
                .observe(viewLifecycleOwner, {
                    when (it.status) {
                        Status.ERROR -> {
                            hide()
                            showToast(it.message)
                        }
                        Status.LOADING -> {
                            show()
                        }
                        Status.SUCCESS -> {
                            hide()
                            binding.CLForgetVerificationCode.visibility = View.VISIBLE
                           // controlResetCodeService()
                            showToast(CHECK_EMAIL_PASSWORD_MESSAGE)

                        }

                    }
                })

        }
    }
    private fun controlResetCodeService() {
        emailStr = binding.ETForgetEmail.text.toString()
        codeStr =
            binding.ETForgetVerificationCode1.text.toString() + binding.ETForgetVerificationCode2.text.toString() +
                    binding.ETForgetVerificationCode3.text.toString() + binding.ETForgetVerificationCode4.text.toString() +
                    binding.ETForgetVerificationCode5.text.toString() + binding.ETForgetVerificationCode6.text.toString()

        accountViewModel.controlResetCode(emailStr, codeStr).observe(
            viewLifecycleOwner,
            {
                when(it.status){
                    Status.SUCCESS -> {
                        hide()
                        showToast(CHANGE_PASSWORD_MESSAGE)
                        binding.CLForgetVerificationCode.visibility = View.GONE
                        binding.TILForgetPassword.visibility = View.VISIBLE
                        binding.TILForgetConfirmationPassword.visibility =  View.VISIBLE
                        //changePasswordService()

                    }
                    Status.ERROR -> {
                        hide()
                        showToast(WRONG_MESSAGE)

                    }
                    Status.LOADING -> {
                        show()

                    }
                }
            })
    }

    private fun changePasswordService() {
        if(checkValidationPassword()){
            accountViewModel.changePassword(getChangePasswordRequestBodyModel()).observe(
                viewLifecycleOwner,{
                    when(it.status){
                        Status.LOADING -> {
                            show()
                        }
                        Status.ERROR -> {
                            hide()
                            showToast(ERROR_MESSAGE)
                        }
                        Status.SUCCESS -> {
                            hide()
                            showToast(SUCCESS_MESSAGE)
                            navController.navigate(R.id.action_global_loginFragment)

                        }
                    }
                }
            )
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}