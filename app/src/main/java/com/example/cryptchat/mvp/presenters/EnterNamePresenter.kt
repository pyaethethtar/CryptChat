package com.example.cryptchat.mvp.presenters

import android.graphics.Bitmap
import com.example.cryptchat.mvp.views.EnterNameView

interface EnterNamePresenter : BasePresenter<EnterNameView> {

    fun onTapCamera()
    fun onTapSave(firstName: String, lastName: String, profileImage: Bitmap?)
}