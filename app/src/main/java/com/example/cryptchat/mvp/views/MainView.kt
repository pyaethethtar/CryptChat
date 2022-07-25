package com.example.cryptchat.mvp.views

interface MainView : BaseView {

    fun navigateToRegister()
    fun displayProfileImage(image: String?)
}