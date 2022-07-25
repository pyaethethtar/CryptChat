package com.example.cryptchat.mvp.presenters.impl

import com.example.cryptchat.data.model.CryptChatModel
import com.example.cryptchat.data.model.CryptChatModelImpl
import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.EnterPhoneNoPresenter
import com.example.cryptchat.mvp.views.EnterPhoneNoView
import com.example.cryptchat.utils.EM_INVALID_PHONE
import com.example.cryptchat.utils.PHONE_NO_CONFIRM_DESCRIPTION
import com.example.cryptchat.utils.PHONE_NO_CONFIRM_TITLE

class EnterPhoneNoPresenterImpl : EnterPhoneNoPresenter, AbstractBasePresenter<EnterPhoneNoView>() {

    private val mModel : CryptChatModel = CryptChatModelImpl

    override fun onTapContinue(phoneNo: String) {
        if (phoneNo!="" && phoneNo.length>=8 && phoneNo.startsWith("09")){
            mView?.displayAlertDialog(PHONE_NO_CONFIRM_DESCRIPTION, PHONE_NO_CONFIRM_TITLE, onTapOkay = {
                mModel.user.phoneNo = phoneNo
                mView?.navigateToOtpScreen()
            })
        }else{
            mView?.displayError(EM_INVALID_PHONE, onTapOkay = {})
        }
    }
}