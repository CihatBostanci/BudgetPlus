package com.example.budgetplus.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.example.budgetplus.R


class GenericTextWatcher(
    private val view: View,
    private val editText: MutableList<EditText>
) : TextWatcher {
    override fun afterTextChanged(editable: Editable) {
        val text = editable.toString()

        when (view.id) {
            R.id.ETVerificationCode1 -> if (text.length == 1) editText[1].requestFocus()
            R.id.ETVerificationCode2 -> if (text.length == 1) editText[2].requestFocus() else if (text.isEmpty()) editText[0].requestFocus()
            R.id.ETVerificationCode3 -> if (text.length == 1) editText[3].requestFocus() else if (text.isEmpty()) editText[1].requestFocus()
            R.id.ETVerificationCode4 -> if (text.length == 1) editText[4].requestFocus() else if (text.isEmpty()) editText[2].requestFocus()
            R.id.ETVerificationCode5 -> if (text.length == 1) editText[5].requestFocus() else if (text.isEmpty()) editText[3].requestFocus()
            R.id.ETVerificationCode6 -> if (text.isEmpty()) editText[4].requestFocus()
            R.id.ETForgetVerificationCode1 -> if (text.length == 1) editText[1].requestFocus()
            R.id.ETForgetVerificationCode2 -> if (text.length == 1) editText[2].requestFocus() else if (text.isEmpty()) editText[0].requestFocus()
            R.id.ETForgetVerificationCode3 -> if (text.length == 1) editText[3].requestFocus() else if (text.isEmpty()) editText[1].requestFocus()
            R.id.ETForgetVerificationCode4 -> if (text.length == 1) editText[4].requestFocus() else if (text.isEmpty()) editText[2].requestFocus()
            R.id.ETForgetVerificationCode5 -> if (text.length == 1) editText[5].requestFocus() else if (text.isEmpty()) editText[3].requestFocus()
            R.id.ETForgetVerificationCode6 -> if (text.isEmpty()) editText[4].requestFocus()
        }
    }

    override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
    override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}

}