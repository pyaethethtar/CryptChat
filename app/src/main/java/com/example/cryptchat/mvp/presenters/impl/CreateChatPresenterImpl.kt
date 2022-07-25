package com.example.cryptchat.mvp.presenters.impl


import com.example.cryptchat.data.model.CryptChatModel
import com.example.cryptchat.data.model.CryptChatModelImpl
import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.CreateChatPresenter
import com.example.cryptchat.mvp.views.CreateChatView

class CreateChatPresenterImpl : CreateChatPresenter, AbstractBasePresenter<CreateChatView>() {

    private val mModel : CryptChatModel = CryptChatModelImpl

    override fun onTapCancel() {
        mView?.navigateToBack()
    }

    override fun onUiReady() {
        if (mModel.userList.isEmpty()){
            mView?.displayEmptyFragment()
        }
        else{
            mView?.displayPeopleFragment()
        }

    }

}