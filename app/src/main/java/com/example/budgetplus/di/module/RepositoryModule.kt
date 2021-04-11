package com.example.budgetplus.di

import com.example.budgetplus.forgetpassword.ForgetPasswordRepository
import com.example.budgetplus.groupdetail.GroupDetailRepository
import com.example.budgetplus.groups.GroupsRepository
import com.example.budgetplus.signup.SignUpRepository
import com.example.budgetplus.login.LoginRepository
import com.example.budgetplus.modalbottomsheet.ModalBottomSheetRepository
import com.example.budgetplus.notification.NotificationRepository
import com.example.budgetplus.service.UserService
import com.example.budgetplus.splash.SplashRepository
import com.example.budgetplus.transactionmodalbottomsheet.TransactionModalBottomSheetRepository
import com.example.budgetplus.verificationcode.VerificationCodeRepository
import org.koin.dsl.module


val repositoryModule = module {

    factory {  SplashRepository (groupService = get(), userService = get()) }
    factory {  LoginRepository (accountService = get(), groupService = get(), userService = get()) }
    factory {  SignUpRepository(accountService = get()) }
    factory {  ModalBottomSheetRepository(groupService = get(), transactionService = get()) }
    factory {  com.example.budgetplus.account.AccountRepository(accountService = get()) }
    factory {  ForgetPasswordRepository(passwordService = get()) }
    factory {  GroupDetailRepository(groupService = get())}
    factory {  NotificationRepository(groupService = get ()) }
    factory {  GroupsRepository(groupService = get()) }
    factory {  TransactionModalBottomSheetRepository(transactionService = get()) }
    factory {  VerificationCodeRepository( emailVerificationService =  get()) }

}