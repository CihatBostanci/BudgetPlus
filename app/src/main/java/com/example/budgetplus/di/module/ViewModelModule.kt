package com.example.budgetplus.di

import com.example.budgetplus.account.AccountViewModel
import com.example.budgetplus.forgetpassword.ForgetPasswordViewModel
import com.example.budgetplus.groupdetail.GroupDetailViewModel
import com.example.budgetplus.groups.GroupsViewModel
import com.example.budgetplus.login.LoginViewModel
import com.example.budgetplus.modalbottomsheet.ModalBottomSheetViewModel
import com.example.budgetplus.notification.NotificationViewModel
import com.example.budgetplus.signup.SignUpViewModel
import com.example.budgetplus.splash.SplashViewModel
import com.example.budgetplus.transactionmodalbottomsheet.TransactionModalBottomSheetViewModel
import com.example.budgetplus.verificationcode.VerificationCodeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { SplashViewModel (splashRepository = get()) }
    viewModel { LoginViewModel  (loginRepository = get()) }
    viewModel { SignUpViewModel (signUpRepository = get()) }
    viewModel { ModalBottomSheetViewModel(modalBottomSheetRepository = get()) }
    viewModel { AccountViewModel(accountRepository = get()) }
    viewModel { ForgetPasswordViewModel(forgetPasswordRepository = get()) }
    viewModel { GroupDetailViewModel(groupDetailRepository = get()) }
    viewModel { NotificationViewModel(notificationRepository = get()) }
    viewModel { GroupsViewModel(groupsRepository = get()) }
    viewModel { TransactionModalBottomSheetViewModel(
        transactionModalBottomSheetRepository = get()) }
    viewModel { VerificationCodeViewModel(verificationCodeRepository = get()) }

}