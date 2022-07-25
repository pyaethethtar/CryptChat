package com.example.cryptchat.view.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemview: View) : RecyclerView.ViewHolder(itemview){

    var mData : T ?= null

    abstract fun bindData(data : T)
}