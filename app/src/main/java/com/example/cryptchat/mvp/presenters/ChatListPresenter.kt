package com.example.cryptchat.mvp.presenters

import com.example.cryptchat.delegate.ChatDelegate
import com.example.cryptchat.mvp.views.ChatListView

interface ChatListPresenter : ChatDelegate, BasePresenter<ChatListView> {

    fun onUiReady()
}