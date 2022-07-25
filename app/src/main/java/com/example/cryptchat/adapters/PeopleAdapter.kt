package com.example.cryptchat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.cryptchat.R
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.delegate.PeopleDelegate
import com.example.cryptchat.view.viewholders.PeopleViewHolder

class PeopleAdapter(val delegate: PeopleDelegate) : BaseAdapter<PeopleViewHolder, UserVO>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_people, parent, false)
        return PeopleViewHolder(delegate, view)
    }
}