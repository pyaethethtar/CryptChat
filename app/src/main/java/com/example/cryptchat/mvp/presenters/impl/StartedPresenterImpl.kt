package com.example.cryptchat.mvp.presenters.impl


import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.StartedPresenter
import com.example.cryptchat.mvp.views.StartedView

class StartedPresenterImpl : AbstractBasePresenter<StartedView>(), StartedPresenter {

    override fun onTapStart() {
        mView?.navigateToEnterPhone()
    }
}