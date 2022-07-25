package com.example.cryptchat.mvp.views

import com.example.cryptchat.data.vos.UserVO

interface ProfileView : BaseView {

    fun displayProfile(profile : UserVO)
    fun navigateToBack()
    fun navigateToProfileEdit()
}