package com.example.cryptchat.mvp.presenters

import com.example.cryptchat.mvp.views.StartedView

interface StartedPresenter : BasePresenter<StartedView> {

    fun onTapStart()
}