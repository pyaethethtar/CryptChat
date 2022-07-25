package com.example.cryptchat.mvp.presenters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.cryptchat.mvp.views.OtpView

interface OtpPresenter : BasePresenter<OtpView> {

    fun onUiReady(activity: FragmentActivity)
    fun onTapVerify(otp: String)
}