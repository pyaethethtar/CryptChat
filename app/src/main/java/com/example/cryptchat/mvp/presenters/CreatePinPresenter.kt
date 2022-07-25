package com.example.cryptchat.mvp.presenters

import com.example.cryptchat.mvp.views.CreatePinView

interface CreatePinPresenter : BasePresenter<CreatePinView> {

    fun onTapNext(pin : String)
}