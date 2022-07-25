package com.example.cryptchat.mvp.views

import android.graphics.Bitmap

interface ProfileEditView : BaseView {

    fun displayProfileInfo(firstName: String, lastName: String?, profileImage: String?)
    fun openGallery()
    fun navigateToBack()
    fun navigateToMain()
}