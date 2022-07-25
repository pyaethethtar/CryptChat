package com.example.cryptchat.mvp.presenters.impl

import com.example.cryptchat.data.model.CryptChatModel
import com.example.cryptchat.data.model.CryptChatModelImpl
import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.ProfilePresenter
import com.example.cryptchat.mvp.views.ProfileView

class ProfilePresenterImpl : ProfilePresenter, AbstractBasePresenter<ProfileView>() {

    private val mModel : CryptChatModel = CryptChatModelImpl

    override fun onUiReady(userId : String) {
        mView?.displayLoading()
        if (userId!=""){
           displayUserInfo(userId)
        }
        else
            displayUserInfo(mModel.user.userId)
            //mView?.displayProfile(mModel.user)
    }

    override fun onTapEdit() {
        mView?.navigateToProfileEdit()
    }

    override fun onTapBack() {
        mView?.navigateToBack()
    }

    private fun displayUserInfo(userId: String){
        mModel.getUser(userId, onSucess = {
            mView?.hideLoading()
            mView?.displayProfile(it)
        }, onFailure = {
            mView?.displayError(it, onTapOkay = {})
        })
    }
}