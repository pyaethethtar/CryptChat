package com.example.cryptchat.mvp.presenters.impl

import android.graphics.Bitmap
import com.example.cryptchat.data.model.CryptChatModel
import com.example.cryptchat.data.model.CryptChatModelImpl
import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.EnterNamePresenter
import com.example.cryptchat.mvp.views.EnterNameView
import com.example.cryptchat.utils.EM_DIFFERENT_PIN
import com.example.cryptchat.utils.EM_INVALID_NAME
import com.example.cryptchat.utils.RSAUtils

class EnterNamePresenterImpl : EnterNamePresenter, AbstractBasePresenter<EnterNameView>() {

    private val mModel : CryptChatModel = CryptChatModelImpl

    override fun onTapCamera() {
        mView?.openGallery()
    }

    override fun onTapSave(firstName: String, lastName: String, profileImage: Bitmap?) {
        if (firstName!=""){
            mView?.displayLoading()
            login(firstName, lastName, profileImage)
        }
        else{
            mView?.displayError(EM_INVALID_NAME, onTapOkay = {})
        }
    }

    private fun login(firstName: String, lastName: String, profileImage: Bitmap?){

        mModel.user.firstName = firstName
        mModel.user.lastName = lastName

        profileImage?.let {
            mModel.profileImage = it
        }

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
}