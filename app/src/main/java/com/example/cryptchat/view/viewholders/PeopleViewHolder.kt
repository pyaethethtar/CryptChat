package com.example.cryptchat.view.viewholders

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.example.cryptchat.activities.ChatActivity
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.delegate.PeopleDelegate
import kotlinx.android.synthetic.main.viewholder_people.view.*

class PeopleViewHolder(val delegate: PeopleDelegate, itemView: View) : BaseViewHolder<UserVO>(itemView) {

    private lateinit var mContext : Context

    override fun bindData(data: UserVO) {

        mContext = itemView.context

        itemView.setOnClickListener {
            delegate.onTapPeople(data.userId)
        }

        itemView.tvContactName.text = data.firstName+" "+data.lastName
        itemView.tvContactPhone.text = data.phoneNo
        if (data.profileImage!=null && data.profileImage!=""){
            Glide.with(mContext).load(data.profileImage).circleCrop().into(itemView.ivPeopleProfileImage)
        }
    }
}