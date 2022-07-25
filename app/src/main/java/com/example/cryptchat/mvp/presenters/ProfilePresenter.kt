package com.example.cryptchat.mvp.presenters

import com.example.cryptchat.mvp.views.ProfileView

interface ProfilePresenter : BasePresenter<ProfileView> {

    fun onUiReady(userId : String)
    fun onTapEdit()
    fun onTapBack()
}