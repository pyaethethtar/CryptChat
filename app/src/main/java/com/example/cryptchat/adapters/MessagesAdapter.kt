package com.example.cryptchat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.cryptchat.R
import com.example.cryptchat.data.vos.MessageVO
import com.example.cryptchat.view.viewholders.MessageViewHolder

class MessagesAdapter : BaseAdapter<MessageViewHolder, MessageVO>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_message, parent, false)
        return MessageViewHolder(view)
    }
}