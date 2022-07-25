package com.example.cryptchat.mvp.presenters

import com.example.cryptchat.mvp.views.BaseView

interface BasePresenter<T : BaseView> {

    fun initPresenter(view: T)
}