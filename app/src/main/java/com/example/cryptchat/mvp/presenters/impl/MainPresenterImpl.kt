package com.example.cryptchat.mvp.presenters.impl

import com.example.cryptchat.data.model.CryptChatModel
import com.example.cryptchat.data.model.CryptChatModelImpl
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.MainPresenter
import com.example.cryptchat.mvp.views.MainView

class MainPresenterImpl : MainPresenter, AbstractBasePresenter<MainView>() {

    private val mModel : CryptChatModel = CryptChatModelImpl
    private var mUser = UserVO()
    private var mUserId : String = ""

    override fun onUiReady(contactList: List<String>) {
        if (!mModel.isLoggedIn()){
            mView?.navigateToRegister()
        }
        else{
            mView?.displayLoading()
            mUserId = mModel.getUserIdFromSharedPref() ?: ""
            mModel.getUser(mUserId, onSucess = {
                mUser = it
                mModel.user = it
                requestUserList(contactList)
                mView?.displayProfileImage(it.profileImage)
            }, onFailure = {
                mView?.displayError(it, onTapOkay = {})
            })


        }
    }

    private fun requestUserList(contactList: List<String>){
        if (contactList.isEmpty()){
            mView?.hideLoading()
        }
        else{
            mModel.getUserList(contactList, onSuccess = {
                mView?.hideLoading()
                if (it.isNotEmpty()){
                    mModel.userList = it
                }
            }, onFailure = {
                mView?.displayError(it, onTapOkay = {})
            })
        }
    }

}