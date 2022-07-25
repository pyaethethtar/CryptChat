package com.example.cryptchat.mvp.presenters

import android.graphics.Bitmap
import com.example.cryptchat.mvp.views.ChatScreenView

interface ChatScreenPresenter : BasePresenter<ChatScreenView> {

    fun onUiReady(chatId: String)
    fun onTapOtherProfile(userId : String)
    fun onTapSend(message : String)
    fun onTapSendImage(image : Bitmap)
    fun onTapBack()
    fun onTapCamera()

}