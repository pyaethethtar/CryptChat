package com.example.cryptchat.view.viewholders

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.example.cryptchat.data.vos.MessageVO
import com.example.cryptchat.utils.USER_AUTHOR
import kotlinx.android.synthetic.main.viewholder_message.view.*
import java.text.SimpleDateFormat

class MessageViewHolder(itemView : View) : BaseViewHolder<MessageVO>(itemView) {

    private lateinit var mContext: Context

    override fun bindData(data: MessageVO) {

        mContext = itemView.context
        data.otherUserImage?.let {
            Glide.with(mContext).load(it).circleCrop().into(itemView.ivProfileImage)
            Glide.with(mContext).load(it).circleCrop().into(itemView.ivProfileImage2)
        }



        if(data.isDateChanged){
            val dateTimeFormat = SimpleDateFormat("MMM dd, hh:mm aa")
            itemView.tvDateTime.text = dateTimeFormat.format(data.timestamp)
            itemView.tvDateTime.visibility= View.VISIBLE
        }
        else{
            itemView.tvDateTime.visibility = View.GONE
        }


        if (data.isLastMessage)
            itemView.ivProfileImage.visibility = View.VISIBLE
        else   itemView.ivProfileImage.visibility = View.INVISIBLE


        if (data.userType== USER_AUTHOR)
            displayAuthorMessage(data)
        else   displayOtherUserMessage(data)
    }

    private fun displayAuthorMessage(message : MessageVO){
        if (message.photoMessage!=null && message.photoMessage!=""){
            Glide.with(mContext).load(message.photoMessage).into(itemView.ivMyMessage)
            itemView.layoutMyPhotoMessage.visibility = View.VISIBLE
            itemView.layoutTheirPhotoMessage.visibility = View.GONE
            itemView.layoutTheirMessage.visibility = View.GONE
            itemView.layoutMyMessage.visibility = View.GONE
        }
        else{
            itemView.tvMyMessage.text = message.message
            itemView.layoutMyMessage.visibility = View.VISIBLE
            itemView.layoutTheirMessage.visibility = View.GONE
            itemView.layoutMyPhotoMessage.visibility = View.GONE
            itemView.layoutTheirPhotoMessage.visibility = View.GONE
        }
    }

    private fun displayOtherUserMessage(message: MessageVO){
        if (message.photoMessage!=null && message.photoMessage!=""){
            Glide.with(mContext).load(message.photoMessage).into(itemView.ivTheirMessage)
            itemView.layoutTheirPhotoMessage.visibility = View.VISIBLE
            itemView.layoutMyPhotoMessage.visibility = View.GONE
            itemView.layoutTheirMessage.visibility = View.GONE
            itemView.layoutMyMessage.visibility = View.GONE
        }
        else{
            itemView.tvTheirMessage.text = message.message
            itemView.layoutTheirMessage.visibility = View.VISIBLE
            itemView.layoutMyMessage.visibility = View.GONE
            itemView.layoutMyPhotoMessage.visibility = View.GONE
            itemView.layoutTheirPhotoMessage.visibility = View.GONE
        }
    }

}