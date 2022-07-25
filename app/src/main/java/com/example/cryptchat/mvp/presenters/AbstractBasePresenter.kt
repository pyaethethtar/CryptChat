package com.example.cryptchat.mvp.presenters

import androidx.lifecycle.ViewModel
import com.example.cryptchat.mvp.views.BaseView

abstract class AbstractBasePresenter<T: BaseView> : BasePresenter<T>, ViewModel() {

    var mView : T ?= null

    override fun initPresenter(view: T) {
        mView = view
    }
}