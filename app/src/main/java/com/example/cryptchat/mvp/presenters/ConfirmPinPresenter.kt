package com.example.cryptchat.mvp.presenters

import com.example.cryptchat.mvp.views.ConfirmPinView

interface ConfirmPinPresenter : BasePresenter<ConfirmPinView> {

    fun onTapConfirm(pin : String)
}