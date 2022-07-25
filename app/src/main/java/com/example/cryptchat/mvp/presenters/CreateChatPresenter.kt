package com.example.cryptchat.mvp.presenters

import com.example.cryptchat.delegate.PeopleDelegate
import com.example.cryptchat.mvp.views.CreateChatView

interface CreateChatPresenter : BasePresenter<CreateChatView> {

    fun onTapCancel()
    fun onUiReady()
}