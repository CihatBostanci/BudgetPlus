package com.example.budgetplus.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.budgetplus.BudgetPlusApplication
import com.example.budgetplus.R
import com.example.budgetplus.databinding.FragmentSignUpBinding
import com.example.budgetplus.manager.SharedPreferencesManager.set
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.model.request.RegisterRequestBodyModel
import com.example.budgetplus.utils.*
import com.example.budgetplus.viewmodel.AccountViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : BaseFragment(), View.OnClickListener, IOnBackPressed {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var email = ""
    private var tokenStr: String? = ""

    //Navigation Component controller
    private lateinit var navController: NavController

    private lateinit var accountViewModel: AccountViewModel

    //View Binding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentSignUpBinding? = null
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
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_sign_up, container, false)
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
        binding.IWSignUpPlusLogo.animation = animationRotate
        binding.TWSignUplogoTitle.animation = animation

       setSpannable()
        //binding.TWReturnLoginText.setOnClickListener(this)
        binding.BTNRegister.setOnClickListener(this)

    }

    private fun setSpannable() {
        val alreadyAccountText = getString(R.string.already_have_an_account_sign_in)
        binding.TWReturnLoginText.highlightColor = requireActivity().getColor(R.color.transparent)

        val ss = SpannableString(alreadyAccountText)
        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                BudgetPlusApplication.sharedPreferencesManager[TOKEN] = ""
                navController.navigate(R.id.action_global_loginFragment)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = requireActivity().getColor(R.color.colorPrimaryDark)
                ds.isUnderlineText = false
            }
        }
        // Span to make text bold
        val bs =  StyleSpan(Typeface.BOLD)
        ss.setSpan(bs, alreadyAccountText.length-8, alreadyAccountText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(clickableSpan1, alreadyAccountText.length-8, alreadyAccountText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.TWReturnLoginText.text = ss
        binding.TWReturnLoginText.movementMethod = LinkMovementMethod.getInstance();

    }

    private fun checkValidation(): Boolean {
        var validationFlag = true
        if (binding.ETRegisterFirstName.text.toString().isEmpty()) {
            binding.TILRegisterFirstName.error = FIRSTNAMEMESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILRegisterFirstName.isErrorEnabled = false
        }
        if (binding.ETRegisterLastName.text.toString().isEmpty()) {
            binding.TILRegisterLastName.error = LASTNAMEMESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILRegisterLastName.isErrorEnabled = false
        }
        if (!EMAIL_ADDRESS_PATTERN.matcher(binding.ETRegisterEmail.text.toString()).matches()) {
            binding.TILRegisterEmail.error = EMAILINVALIDMESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILRegisterEmail.isErrorEnabled = false
        }

        if (binding.ETRegisterPassword.text?.length !in 5..12 &&
            !isValidPassword(binding.ETRegisterPassword.text.toString())
        ) {
            binding.TILRegisterPassword.error = PASSWORDINVALIDMESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILRegisterPassword.isErrorEnabled = false
        }
        if (binding.ETRegisterConfirmationPassword.text.toString().isEmpty()
            || binding.ETRegisterConfirmationPassword.text.toString()
            != binding.ETRegisterPassword.text.toString()
        ) {
            binding.TILRegisterConfirmationPassword.error =
                FORGETPASSWORDERRORMESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILRegisterConfirmationPassword.isErrorEnabled = false
        }

        return validationFlag

    }

    private fun getRegisterRequestModel() = RegisterRequestBodyModel().apply {
        email = binding.ETRegisterEmail.text.toString()//"cihattest@email.com"
        password = binding.ETRegisterPassword.text.toString()//"naberLo54"
        firstName = binding.ETRegisterFirstName.text.toString() //"Cihat"
        lastName = binding.ETRegisterLastName.text.toString()//"Bostanci"
        confirmPassword = binding.ETRegisterConfirmationPassword.text.toString() //"naberLo54"
    }

    private fun getLoginRequestModel() = LoginRequestBodyModel().apply {
        email = binding.ETRegisterEmail.text.toString()
        password = binding.ETRegisterPassword.text.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
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
                binding.BTNRegister.id -> {
                    registerAction()
                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        BudgetPlusApplication.sharedPreferencesManager[TOKEN] = ""
        return true
    }

    private fun registerAction() {
        if (checkValidation()) {
            accountViewModel.accountRegister(getRegisterRequestModel()).observe(viewLifecycleOwner,
                {
                    Log.d(LOGINFRAGMENTTAG, it.toString())
                    when (it.status) {
                        Status.ERROR -> {
                            hide()
                            showToast(it.message)

                            //code:3 if swagger
                            //toVerificationFragment()

                        }
                        Status.SUCCESS -> {
                            it?.let {
                                it.data?.let { dataRegister ->
                                    hide()
                                    if (dataRegister.isRegistered) {
                                        email = dataRegister.email
                                        actionAuthenticate()
                                    }
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

    private fun actionAuthenticate() {

        accountViewModel.authenticateAccount(getLoginRequestModel()).observe(
            viewLifecycleOwner,
            {
                when (it.status) {
                    Status.ERROR -> {
                        hide()
                        showToast(it.message)
                    }
                    Status.LOADING -> {
                        show()
                    }
                    Status.SUCCESS -> {

                        val loginSuccessResponseModel = it.data
                        tokenStr = loginSuccessResponseModel?.token
                        BudgetPlusApplication.sharedPreferencesManager[TOKEN] = tokenStr
                        hide()
                        toVerificationFragment()

                    }
                }
            }
        )
    }

    private fun toVerificationFragment() {
        val emailBundle =
            bundleOf(
                "customerEmail" to binding.ETRegisterEmail.text.toString(),
                "token" to tokenStr,
                "customerPassword" to binding.ETRegisterPassword.text.toString()
            )
        navController.navigate(R.id.action_signUpFragment_to_verificationCodeFragment, emailBundle)
    }
}