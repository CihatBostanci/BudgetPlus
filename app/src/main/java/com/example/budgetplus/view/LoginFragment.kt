package com.example.budgetplus.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
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
import com.example.budgetplus.databinding.FragmentLoginBinding
import com.example.budgetplus.manager.SharedPreferencesManager.get
import com.example.budgetplus.manager.SharedPreferencesManager.set
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.model.response.LoginSuccessResponseModel
import com.example.budgetplus.utils.*
import com.example.budgetplus.viewmodel.AccountViewModel
import com.example.budgetplus.viewmodel.GroupViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

const val LOGINFRAGMENTTAG = "LOGINFTAG"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : BaseFragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var tokenStr: String? = ""

    //Navigation Component controller
    private lateinit var navController: NavController

    //View Binding
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var accountViewModel: AccountViewModel

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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.IWLoginPlusLogo.animation = animationRotate
        binding.TWLoginlogoTitle.animation = animation

        binding.BTNLogin.setOnClickListener(this)
        binding.TWForgetText.setOnClickListener(this)
        setSpannable()

        val token = BudgetPlusApplication.sharedPreferencesManager[TOKEN, ""]
        if (token != null) {
            if (token.isNotEmpty()) {
                tokenStr = token
                navController.navigate(R.id.action_loginFragment_to_groupsFragment)
            }
        }
    }


    private fun setSpannable() {
        binding.TWDontHaveAccountText.highlightColor = requireActivity().getColor(R.color.transparent)
        val alreadyAccountText = getString(R.string.login_signup_hint)
        val ss = SpannableString(alreadyAccountText)
        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                navController.navigate(R.id.action_loginFragment_to_signUpFragment)
            }


            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = requireActivity().getColor(R.color.colorPrimaryDark)
                ds.isUnderlineText = false
            }
        }
        // Span to make text bold
        val bs =  StyleSpan(Typeface.BOLD)
        ss.setSpan(bs,alreadyAccountText.length-9, alreadyAccountText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(clickableSpan1, alreadyAccountText.length-9, alreadyAccountText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.TWDontHaveAccountText.text = ss
        binding.TWDontHaveAccountText.movementMethod = LinkMovementMethod.getInstance();

    }

    private fun checkValidation(): Boolean {
        var validationFlag = true
        if (!EMAIL_ADDRESS_PATTERN.matcher(binding.ETLoginEmail.text.toString()).matches()) {
            binding.TILLoginEmail.error = EMAILINVALIDMESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILLoginEmail.isErrorEnabled = false
        }
        if (binding.ETLoginPassword.text.length !in 5..12 &&
            !isValidPassword(binding.ETLoginPassword.text.toString())
        ) {
            binding.TILLoginPassword.error = PASSWORDINVALIDMESSAGE
            validationFlag = false
            return validationFlag
        } else {
            binding.TILLoginPassword.isErrorEnabled = false
        }
        return validationFlag
    }

    private fun authenticateService() {
        if (checkValidation()) {
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

                            it?.let {
                                it.data.let { loginSuccessReturnModel ->
                                    hide()
                                    tokenStr = loginSuccessReturnModel?.token
                                    BudgetPlusApplication.sharedPreferencesManager[TOKEN] = tokenStr
                                    loginSuccessReturnModel?.isEmailConfirmed?.let { isEmailConfirmed ->
                                        if (isEmailConfirmed) {
                                            navController.navigate(R.id.action_loginFragment_to_groupsFragment)
                                        } else {
                                            toVerificationFragment()
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            )

        }
    }

    private fun loginService(loginSuccessResponseModel: LoginSuccessResponseModel?) {

        loginSuccessResponseModel?.let {
            it.token?.let {
                accountViewModel.accountLogin(getLoginRequestModel(), it).observe(
                    viewLifecycleOwner,
                    {
                        Log.d(LOGINFRAGMENTTAG, it.toString())
                        when (it.status) {
                            Status.ERROR -> {
                                hide()
                                showToast(it.message)

                            }
                            Status.SUCCESS -> {

                            }
                            Status.LOADING -> {
                                show()
                            }
                        }
                    }
                )
            }
        }
    }

    private fun toVerificationFragment() {
        val emailBundle =
            bundleOf(
                "customerEmail" to binding.ETLoginEmail.text.toString(),
                "token" to tokenStr,
                "customerPassword" to binding.ETLoginPassword.text.toString()
            )
        navController.navigate(R.id.action_loginFragment_to_verificationCodeFragment, emailBundle)
    }

    private fun getLoginRequestModel() = LoginRequestBodyModel().apply {
        email = binding.ETLoginEmail.text.toString()
        password = binding.ETLoginPassword.text.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
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
                binding.BTNLogin.id -> {
                    authenticateService()
                }
                binding.TWForgetText.id -> {
                    navController.navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
                }
            }
        }

    }
}