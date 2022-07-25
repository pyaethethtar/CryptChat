package com.example.cryptchat.mvp.presenters

import com.example.cryptchat.delegate.PeopleDelegate
import com.example.cryptchat.mvp.views.PeopleListView

interface PeopleListPresenter : PeopleDelegate, BasePresenter<PeopleListView> {

    fun onUiReady(contacts : ArrayList<String>)
}