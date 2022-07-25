package com.example.cryptchat.mvp.presenters

import com.example.cryptchat.mvp.views.EnterPhoneNoView

interface EnterPhoneNoPresenter : BasePresenter<EnterPhoneNoView> {

    fun onTapContinue(phoneNo : String)
}