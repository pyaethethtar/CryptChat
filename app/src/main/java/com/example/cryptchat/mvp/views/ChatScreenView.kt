package com.example.cryptchat.mvp.views

import android.media.Image
import com.example.cryptchat.data.vos.MessageVO
import com.example.cryptchat.data.vos.UserVO

interface ChatScreenView : BaseView {

    fun displayUserInfo(info: UserVO)
    fun displayMessages(messages : List<MessageVO>)
    fun navigateToUserProfile(userId : String)
    fun navigateToBack()
    fun openGallery()
    fun clearInputTextBox()
}