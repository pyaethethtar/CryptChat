package com.example.cryptchat.mvp.presenters.impl

import com.example.cryptchat.data.model.CryptChatModel
import com.example.cryptchat.data.model.CryptChatModelImpl
import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.ConfirmPinPresenter
import com.example.cryptchat.mvp.views.ConfirmPinView
import com.example.cryptchat.utils.EM_DIFFERENT_PIN
import com.example.cryptchat.utils.RSAUtils

class ConfirmPinPresenterImpl : ConfirmPinPresenter, AbstractBasePresenter<ConfirmPinView>() {

    val mModel : CryptChatModel = CryptChatModelImpl

    override fun onTapConfirm(pin: String) {
        if (mModel.user.pin==pin){
            mView?.displayLoading()

            //generate key pair
            val keypair = RSAUtils.getKeyPair()
            mModel.user.publicString = keypair.first

            mModel.login(mModel.user, mModel.profileImage, onSucess = {
                mModel.saveUserIdInSharedPref(it)
                mModel.savePrivateString(keypair.second)
                mView?.hideLoading()
                mView?.navigateToMainScreen()
            }, onFailure = {
                mView?.displayError(EM_DIFFERENT_PIN, onTapOkay = {})
            })
        }
        else{
            mView?.displayError(EM_DIFFERENT_PIN, onTapOkay = {})
        }
    }


}

