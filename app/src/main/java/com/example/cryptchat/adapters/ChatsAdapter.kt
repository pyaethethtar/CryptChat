package com.example.cryptchat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.cryptchat.R
import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.delegate.ChatDelegate
import com.example.cryptchat.view.viewholders.ChatViewHolder

class ChatsAdapter(val delegate: ChatDelegate) : BaseAdapter<ChatViewHolder, ChatVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_chat, parent, false)
        return ChatViewHolder(view, delegate)
    }
}