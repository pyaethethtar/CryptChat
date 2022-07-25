package com.example.cryptchat.view.viewholders

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.example.cryptchat.activities.ChatActivity
import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.delegate.ChatDelegate
import kotlinx.android.synthetic.main.viewholder_chat.view.*
import java.text.SimpleDateFormat

class ChatViewHolder(itemView: View, val delegate: ChatDelegate) : BaseViewHolder<ChatVO>(itemView) {

    private var friend = UserVO()

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun bindData(data: ChatVO) {

        if (data.messages.isNotEmpty()){

            itemView.setOnClickListener {
                delegate.onTapChatItem(data.chatId)
            }

            if (data.user1.isAuthor){
                friend = data.user2
            }
            else{
                friend = data.user1
            }
            itemView.tvChatListProfileName.text = friend.firstName+" "+friend.lastName
            if (friend.profileImage!=null && friend.profileImage!=""){
                Glide.with(itemView.context).load(friend.profileImage).circleCrop().into(itemView.ivChatListProfileImage)
            }

            val sdf = SimpleDateFormat("EEE")
            itemView.tvDate.text = sdf.format(data.messages.last().timestamp)
        }
    }

}