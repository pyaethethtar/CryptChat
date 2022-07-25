package com.example.cryptchat.mvp.presenters

import com.example.cryptchat.mvp.views.MainView

interface MainPresenter : BasePresenter<MainView> {

    fun onUiReady(contactList : List<String>)
}