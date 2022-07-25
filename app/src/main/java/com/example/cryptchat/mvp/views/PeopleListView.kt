package com.example.cryptchat.mvp.views

import com.example.cryptchat.data.vos.UserVO

interface PeopleListView : BaseView {

    fun displayPeopleList(people : List<UserVO>)
    fun displayEmptyList()
    fun navigateToChatScreen(chatId : String)
}