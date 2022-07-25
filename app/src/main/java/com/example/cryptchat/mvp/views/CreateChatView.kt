package com.example.cryptchat.mvp.views

import com.example.cryptchat.data.vos.UserVO

interface CreateChatView : BaseView {

    fun displayPeopleFragment()
    fun navigateToBack()
    fun displayEmptyFragment()
}