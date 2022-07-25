package com.example.cryptchat.mvp.views

import com.example.cryptchat.data.vos.ChatVO

interface ChatListView : BaseView {

    fun displayChatList(chatList: List<ChatVO>)
    fun displayEmptyList()
    fun navigateToChatScreen(id : String)
}