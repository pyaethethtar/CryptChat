package com.example.cryptchat.mvp.presenters

import android.graphics.Bitmap
import com.example.cryptchat.mvp.views.ProfileEditView

interface ProfileEditPresenter : BasePresenter<ProfileEditView> {

    fun onUiReady()
    fun onTapBack()
    fun onTapCamera()
    fun onTapSave(firstName: String, lastName: String?, profileImage: Bitmap?)
}